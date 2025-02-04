package main

import (
	"fmt"
	"net/http"
)

func helloHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hello, world!")
}

func main() {
	http.HandleFunc("/", helloHandler)
	fmt.Println("Server started at :8080")
	http.ListenAndServe(":8080", nil)
}
