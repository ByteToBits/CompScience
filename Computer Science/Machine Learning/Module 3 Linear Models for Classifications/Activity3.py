import numpy as np
import matplotlib.pyplot as plt

def make_gaussian_mixture_data(n, means, covs=None, class_probs=None, random_state=None):
    """Generates data drawn from a Gaussian mixture.

    Args:
        n (int): number of data points to generate 
        means: sequence of mean vectors, one element per mixture component
        covs: sequence of covariance matrices, one element per mixture component, if None will default to unit covariance
        class_probs: vector of component probabilities, if None will default to uniform
        random_state: seed or random number generator
    """
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


def scatter_data_by_target_value(x, y, ax=None, scatter_params={'ec': 'black', 'alpha': 0.8}):
    res = []
    ax = plt.gca() if ax is None else ax
    for c in range(2):
        x1_c = x[y==c, 0]
        x2_c = x[y==c, 1]
        res.append(ax.scatter(x1_c, x2_c, label=f'$c={c}$', **scatter_params))
    ax.legend()
    ax.set_xlabel('$x_1$')
    ax.set_ylabel('$x_2$')
    return res


def plot_line(slope, intercept, ax=None, shape='--', **kwargs):
    ax = plt.gca() if ax is None else ax
    x_vals = np.array(ax.get_xlim())
    y_vals = intercept + slope * x_vals
    ax.set_ylim(ax.get_ylim())
    ax.set_xlim(ax.get_xlim())
    ax.plot(x_vals, y_vals, shape, **kwargs)


def plot_decision_boundary_from_weights(weights, ax=None):
    if len(weights)==2:
        slope = -weights[0]/weights[1]
        intercept = 0
    else:
        slope = -weights[1]/weights[2]
        intercept = -weights[0]/weights[2]
    plot_line(slope, intercept, ax, shape='--', color='red')
