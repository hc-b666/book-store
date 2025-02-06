package handlers

import (
	"book-store-server/database"
	"book-store-server/models"
	"encoding/json"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
)

func FindBooks(w http.ResponseWriter, r *http.Request) {
	var books []models.Book
	database.DB.Preload("Genres").Find(&books)
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(books)
}

func FindBook(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	params := mux.Vars(r)
	id, _ := strconv.Atoi(params["id"])

	var book models.Book
	if err := database.DB.Preload("Genres").First(&book, id).Error; err != nil {
		http.Error(w, "Book not found", http.StatusNotFound)
		return
	}

	json.NewEncoder(w).Encode(book)
}

func CreateBook(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var book models.Book
	if err := json.NewDecoder(r.Body).Decode(&book); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	if err := database.DB.Create(&book).Error; err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	json.NewEncoder(w).Encode(book)
}

func UpdateBook(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	params := mux.Vars(r)
	id, _ := strconv.Atoi(params["id"])

	var updatedBook models.Book
	if err := json.NewDecoder(r.Body).Decode(&updatedBook); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	var book models.Book
	if err := database.DB.First(&book, id).Error; err != nil {
		http.Error(w, "Book not found", http.StatusNotFound)
		return
	}

	book.Title = updatedBook.Title
	book.Description = updatedBook.Description
	book.ISBN = updatedBook.ISBN
	book.Genres = updatedBook.Genres
	book.PageCount = updatedBook.PageCount
	book.Author = updatedBook.Author
	book.Publisher = updatedBook.Publisher
	book.PublishedDate = updatedBook.PublishedDate
	book.Price = updatedBook.Price
	book.Stock = updatedBook.Stock

	if err := database.DB.Save(&book).Error; err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	json.NewEncoder(w).Encode(book)
}

func DeleteBook(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	params := mux.Vars(r)
	id, _ := strconv.Atoi(params["id"])

	if err := database.DB.Delete(&models.Book{}, id).Error; err != nil {
		http.Error(w, "Book not found", http.StatusNotFound)
		return
	}

	w.WriteHeader(http.StatusNoContent)
}
