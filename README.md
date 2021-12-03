# Concurrent image processing in multu-singe modes

Java used for this project

## Installation

JDK installation is required for compiling and running the project


## Commands for compiling project
```bash
javac Main.java
```
This Main class contains the entry point(main method) for running the project. 
I have two other classes(MultiThread and Picture), but compiling the main class
will automatically create needed to compile files for project

## Commands for running project

```bash
java Main [imagePath : absolute path to image preferred] [chunkSize : positive integer] [Mode : S/M]
```

## For example
```bash
java Main C:/img.png 10 S
```
## Logic of program(How it works)
In Single mode it will run through pixels one by one and transform them
In MultipleThread mode it will run through pixels in parallel and transform several pictures