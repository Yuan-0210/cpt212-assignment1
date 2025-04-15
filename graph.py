# Plot the graph
import matplotlib.pyplot as plt

# Generate the n values from 8 to 80 in steps of 8
n_values = list(range(8, 81, 8))
# Calculate the corresponding counter values using the linear relation:
# counter = 574 when n=8 and increases by 360 for every additional 8 in n.
counter_values = [574 + 360 * ((n // 8) - 1) for n in n_values]

# Create the plot
plt.figure(figsize=(8, 5))  # Set the figure size
plt.plot(n_values, counter_values, marker='o', linestyle='-', color='b')  # Plot with circle markers, solid line, in blue
plt.title('number of primitive operation vs n')  # Set the title of the plot
plt.xlabel('n')  # Label the x-axis
plt.ylabel('Number of Primitive Operations')  # Label the y-axis
plt.grid(True)  # Enable grid for better readability
plt.xticks(n_values)  # Set x-ticks to show all n values for clarity
plt.show()  # Display the plot


# Determine the big-O
# The graph increases linearly shows that the function is proportional to n.
# The number of primitive operations increase by 360 when n increases by 8.
# Big-O time complexity for the radix sort is O(n).

