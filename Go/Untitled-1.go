package main

import "time"

const N = 20

func producer(out chan<- int) {
	for i := 0; i < N; i++ {
		out <- i
	}
}
func adder(in <-chan int, out chan<- int, val int) {
	for {
		x := <-in
		out <- x + val
	}
}
func multiplier(in <-chan int, out chan<- int, val int) {
	for {
		x := <-in
		out <- x * val
	}
}
func consumer(in <-chan int, done chan<- bool, val int) {
	for i := 0; i < N; i++ {
		x := <-in
		if i%val == 0 {
			print(x, "\n")
		}
	}
	done <- true
}

func consumer_new(in <-chan int, val int) {
	for i := 0; i < N; i++ {
		x := <-in
		if i%val == 0 {
			print(x, "\n")
		}
	}

}

func main() {
	add_val := 3
	mult_val := 2
	filter_val := 3
	ch1 := make(chan int)
	ch2 := make(chan int)
	ch3 := make(chan int)
	done := make(chan bool)
	go producer(ch1)
	go adder(ch1, ch2, add_val)
	go multiplier(ch2, ch3, mult_val)
	go consumer(ch3, done, filter_val)
	time.Sleep(2 * time.Second)
}
