package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	ch := make(chan int, 10)
	done := make(chan bool)

	go producer(ch, done)
	go consumer(ch, done)

	<-done
	<-done

	fmt.Printf("%d\n", <-ch)

}

func producer(c chan<- int, done chan<- bool) {
	for i := 0; i < 1000; i++ {
		time.Sleep(time.Duration(rand.Intn(10)) * time.Millisecond)
		c <- i + 1
	}
	done <- true
}

func consumer(c chan int, done chan<- bool) {
	j := 0
	for i := 0; i < 1000; i++ {
		time.Sleep(time.Duration(rand.Intn(10)) * time.Millisecond)
		j = <-c
	}
	done <- true
	c <- j
}
