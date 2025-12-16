# Parallel Mandelbrot Renderer in Java

This project implements a **parallel and concurrent Mandelbrot fractal renderer** in Java.  
It was developed as part of a course on **Concurrent and Parallel Programming**, with the goal of comparing different parallelization strategies on a CPU-bound workload.

The Mandelbrot set is a classic example of an *embarrassingly parallel* problem, where each pixel can be computed independently. This makes it an excellent benchmark to study scalability and performance.

---

## 🚀 Features

- Sequential Mandelbrot implementation (baseline)
- Parallel implementation using **ExecutorService** (fixed thread pool)
- Parallel implementation using **ForkJoinPool**
- Performance comparison with execution time and speedup
- Automatic generation of fractal images in PNG format
- Maven-based project structure

---

## 🧠 Implemented Approaches

### 1. Sequential Version
Computes the Mandelbrot fractal using nested loops.  
This version is used as the baseline for all performance comparisons.

### 2. Parallel Version – ExecutorService
- Uses a fixed-size thread pool
- Each task computes one image row
- Excellent load balancing and low overhead
- Achieves near-linear speedup up to the number of physical CPU cores

### 3. Parallel Version – ForkJoin Framework
- Uses recursive task splitting (`RecursiveAction`)
- Based on divide-and-conquer and work-stealing
- More general, but higher overhead for this specific problem

---

## 📊 Performance Summary

| Method / Threads | Time (ms) | Speedup |
|------------------|-----------|---------|
| Sequential       | 1542      | 1.00    |
| Parallel (1)     | 1539      | 1.00    |
| Parallel (2)     | 755       | 2.04    |
| Parallel (4)     | 401       | 3.85    |
| Parallel (8)     | 223       | 6.91    |
| Parallel (16)    | 250       | 6.17    |
| ForkJoinPool     | 328       | 4.70    |

The fixed thread pool implementation provides the best performance for this workload.

---

## 🛠️ Requirements

- Java **17** or later
- Apache **Maven 3.8+**

---

## ▶️ How to Build and Run

Clone the repository and move into the project directory:

```bash
git clone https://github.com/your-username/parallel-mandelbrot-java.git
cd parallel-mandelbrot-java

# Compile and build the project
mvn clean install
mvn clean package
mvn exec:java

```
