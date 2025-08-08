import numpy as np
  
learning_rate = 0.1
activation_function = 'step'  # 'step' or 'sigmoid'
step_function_boundary = 0

# Configuration 1: AND Gate (3 weights, 2 inputs)
initial_weights = np.array([-1.0, 1.0, 0.0])
training_data = [
    ([0, 0], 0),  # Without bias - will be auto-added
    ([0, 1], 0),
    ([1, 0], 0),
    ([1, 1], 1),
]

# Configuration 2: Custom Problem (4 weights, 3 inputs)
# initial_weights = np.array([2.0, 1.0, -3.0, 4.0])  
# training_data = [
#     ([0, 1, 1], 1),  # x₁=0, x₂=1, x₃=1 (bias auto-added)
#     ([0, 0, 1], 1),  # x₁=0, x₂=0, x₃=1
#     ([1, 0, 1], 0),  # x₁=1, x₂=0, x₃=1
#     ([0, 1, 0], 0)   # x₁=0, x₂=1, x₃=0
# ]

max_epochs = 10

class SimplePerceptron:
    def __init__(self, weights, learning_rate=0.1, activation='step'):
        self.weights = np.array(weights, dtype=float)
        self.lr = learning_rate
        self.activation = activation
    
    def activate(self, z):
        if self.activation == 'step':
            return 1 if z > step_function_boundary else 0
        elif self.activation == 'sigmoid':
            return 1 / (1 + np.exp(-np.clip(z, -500, 500)))
    
    def train_step(self, x_without_bias, target):
        # Automatically add bias term (x₀=1) at the beginning
        x = np.array([1] + list(x_without_bias))
        
        # Check if dimensions match
        if len(x) != len(self.weights):
            raise ValueError(f"Input size mismatch: got {len(x_without_bias)} inputs, "
                           f"but weights expect {len(self.weights)-1} inputs (plus bias)")
        
        z = np.dot(self.weights, x)
        prediction = self.activate(z)
        
        # Display with proper x₀, x₁, x₂... format
        input_str = f"x0=1"
        for i, val in enumerate(x_without_bias):
            input_str += f", x{i+1}={val}"
        
        print(f"Row: {input_str}, target={target}")
        print(f"Z = {round(z,2)} | g({round(z,2)}) = {prediction}", end="")

        error_threshold = 0.5 if self.activation == 'sigmoid' else 0
        if abs(prediction - target) > error_threshold:
            error = target - prediction
            print(f" (Incorrect, should be {target})")
            print(f"Old weights: {np.round(self.weights, 2)}")
            self.weights += self.lr * error * x
            print(f"New weights: {np.round(self.weights, 2)}")
            print()
            return True
        else:
            print(" (Correct)")
            return False

# Validate configuration
expected_inputs = len(initial_weights) - 1  # -1 for bias weight
actual_inputs = len(training_data[0][0])
if expected_inputs != actual_inputs:
    raise ValueError(f"Configuration error: {len(initial_weights)} weights expect "
                   f"{expected_inputs} inputs, but training data has {actual_inputs} inputs")

print(f"{activation_function.upper()} Activation")
print(f"Initial weights: {initial_weights}")
print(f"Learning rate: {learning_rate}")
print(f"Step boundary: {step_function_boundary}")
print()

perceptron = SimplePerceptron(initial_weights.copy(), learning_rate, activation_function)

for epoch in range(1, max_epochs + 1):
    print(f"Epoch {epoch}")
    print(f"Current weights: {np.round(perceptron.weights, 2)}")
    print()
    
    errors = 0
    for i, (x, target) in enumerate(training_data, 1):
        print(f"Testing Row {i}:")
        if perceptron.train_step(x, target):
            errors += 1
    
    if errors == 0:
        print("All samples classified correctly!")
        break
    print()

print("Final Results")
print(f"Final weights: {np.round(perceptron.weights, 2)}")