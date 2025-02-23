package routes

import (
	"book-store-server/internals/handlers"
	"book-store-server/internals/middlewares"
	"fmt"
	"net/http"

	"github.com/gorilla/mux"
)

func helloHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hello, world!")
}

func SetupRoutes() *mux.Router {
	r := mux.NewRouter()

	r.Use(middlewares.LoggingMiddleware)

	r.HandleFunc("/", helloHandler)
	r.HandleFunc("/books", handlers.FindBooks).Methods("GET")
	r.HandleFunc("/books/{id}", handlers.FindBook).Methods("GET")
	r.HandleFunc("/books", handlers.CreateBook).Methods("POST")
	r.HandleFunc("/books/{id}", handlers.UpdateBook).Methods("PUT")
	r.HandleFunc("/books/{id}", handlers.DeleteBook).Methods("DELETE")

	return r
}
