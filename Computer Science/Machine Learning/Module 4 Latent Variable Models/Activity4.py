import numpy as np
from matplotlib import pyplot as plt

class BootstrapSplitter:

    def __init__(self, reps, train_size, random_state=None):
        self.reps = reps
        self.train_size = train_size
        self.RNG = np.random.default_rng(random_state)

    def get_n_splits(self):
        return self.reps

    def split(self, x, y=None, groups=None):
        for _ in range(self.reps):
            train_idx = self.RNG.choice(np.arange(len(x)), size=round(self.train_size*len(x)), replace=True)
            test_idx = np.setdiff1d(np.arange(len(x)), train_idx)
            np.random.shuffle(test_idx)
            yield train_idx, test_idx

def plot_scatter_by_label(X, y, ax=None, title=None):
    if ax is None:
        fig, ax = plt.subplots(1, 1)
        fig.set_figheight(6)
        fig.set_figwidth(8)
    categories = np.unique(y)
    for cat in categories:
        ax.scatter(X[y==cat, 0], X[y==cat, 1], label='Cluster {0}'.format(cat), alpha=0.25)
        ax.scatter(np.mean(X[y==cat, 0]), np.mean(X[y==cat, 1]), c='k', marker='x', s=200)
    ax.set_xlabel('X0', size=15)
    ax.set_ylabel('X1', size=15)
    ax.set_title(title if title else "Gaussian Mixture", size=20)
    plt.legend()
    return ax

def plot_gmm(gmm, x, gran=3):
    # plotting every 10 iterations
    idxs = np.arange(start=0, stop=gmm.r_historic_.shape[-1], step=gran)
    n_plots = len(idxs)
    rows = int(np.sqrt(n_plots))
    cols = int(n_plots//rows) + 1
    fig, axs = plt.subplots(rows, cols)
    fig.set_figheight(5*rows)
    fig.set_figwidth(5*cols)
    for i, idx in enumerate(idxs):
        if not(1 in (rows, cols)):
            j = int(i//cols)
            k = int(i%cols)
            axs[j, k].scatter(x[:,0], x[:,1], c=gmm.r_historic_[:, :, idx], alpha=0.25) # coloring with r will create a mix of RGB colors based on posterior of each point
            axs[j, k].scatter(gmm.Mu_hat_historic_[:,0,idx], gmm.Mu_hat_historic_[:,1,idx], s=200, c='black', marker='x')
            axs[j, k].set_title('EM results (tau={0})'.format(idx))
            axs[j, k].set_xlabel('x1')
            axs[j, k].set_ylabel('x2')
        else:
            axs[i].scatter(x[:,0], x[:,1], c=gmm.r_historic_[:, :, idx], alpha=0.25) # coloring with r will create a mix of RGB colors based on posterior of each point
            axs[i].scatter(gmm.Mu_hat_historic_[:,0,idx], gmm.Mu_hat_historic_[:,1,idx], s=200, c='black', marker='x')
            axs[i].set_title('EM results (tau={0})'.format(idx))
            axs[i].set_xlabel('x1')
            axs[i].set_ylabel('x2')
    return fig, axs

def make_gaussian_mixture_data(n, means, covs=None, class_probs=None, random_state=None):
    RNG = np.random.default_rng(seed=random_state)
    d = len(means[0])
    k = len(means)

    # sample outputs
    # if no class probabilities are provided, assume uniform
    class_probs=np.ones(k)/k if class_probs is None else class_probs

    # generate the y-sample using a multinomial distribution with 'number of experiments' equal to 1;
    # this results in a categorical distribution
    # the output of multinomial is a n times x binary matrix with a single 1-entry per row indicating
    # what class that row belongs to; we map this to the numbers 0 to (k-1) with np.nonzero
    _, y = np.nonzero(RNG.multinomial(1, class_probs, size=n))

    # sample inputs conditioned on outputs
    # if no covariances are provided assume unit
    covs = [np.eye(d) for _ in range(k)] if covs is None else covs
    x = np.zeros(shape=(n, d))
    for i in range(k):
        idx_i = np.flatnonzero(y==i)
        x[idx_i] = RNG.multivariate_normal(means[i], covs[i], size=len(idx_i))

    return x, y

if __name__ == "__main__":
    p0 = 0.60; p1 = 1 - p0
    class_probs = [p0, p1] 
    mu0 = np.array([3.5, 2.5])
    mu1 = np.array([0.0, 6.0])

    make_gaussian_mixture_data(10, [mu0, mu1], class_probs=class_probs, random_state=0)
