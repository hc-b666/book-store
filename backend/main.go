package main

import (
	"encoding/json"
	"fmt"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
)

type Book struct {
	Id    int    `json:"id"`
	Title string `json:"title"`
}

var books []Book
var idCounter int = 1

func helloHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hello, world!")
}

func main() {
	r := mux.NewRouter()

	r.HandleFunc("/", helloHandler)

	r.HandleFunc("/books", findBooks).Methods("GET")
	r.HandleFunc("/books/{id}", findBook).Methods("GET")
	r.HandleFunc("/books", createBook).Methods("POST")
	r.HandleFunc("/books/{id}", updateBook).Methods("PUT")
	r.HandleFunc("/books/{id}", deleteBook).Methods("DELETE")

	fmt.Println("Server started at :8080")
	http.ListenAndServe(":8080", r)
}

func findBooks(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(books)
}

func findBook(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	params := mux.Vars(r)
	id, _ := strconv.Atoi(params["id"])

	for _, book := range books {
		if book.Id == id {
			json.NewEncoder(w).Encode(book)
			return
		}
	}

	http.Error(w, "Book not found", http.StatusNotFound)
}

func createBook(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var book Book
	_ = json.NewDecoder(r.Body).Decode(&book)

	book.Id = idCounter
	idCounter++

	books = append(books, book)

	json.NewEncoder(w).Encode(book)
}

func updateBook(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	params := mux.Vars(r)
	id, _ := strconv.Atoi(params["id"])

	var updatedBook Book
	_ = json.NewDecoder(r.Body).Decode(&updatedBook)

	for index, item := range books {
		if item.Id == id {
			books[index].Title = updatedBook.Title
			json.NewEncoder(w).Encode(books[index])
			return
		}
	}

	http.Error(w, "Book not found", http.StatusNotFound)
}

func deleteBook(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	params := mux.Vars(r)
	id, _ := strconv.Atoi(params["id"])

	for index, book := range books {
		if book.Id == id {
			books = append(books[:index], books[index+1:]...)
			w.WriteHeader(http.StatusNoContent)
			return
		}
	}

	http.Error(w, "Book not found", http.StatusNotFound)
}
