package models

import "gorm.io/gorm"

type Book struct {
	gorm.Model

	Title       string `json:"title"`
	Description string `json:"description"`
	ISBN        string `json:"isbn"`
	Genre       string `json:"genre"`
	PageCount   string `json:"page_count"`

	// Genres      []Genre `gorm:"many2many:book_genres;" json:"genres"`

	Author string `json:"author"`

	Publisher     string `json:"publisher"`
	PublishedDate string `json:"published_date"`

	Price float64 `json:"price"`
	Stock int     `json:"stock"`

	ImageUrl           *string `json:"image_url"`
	BackgroundImageUrl *string `json:"background_image_url"`
}

type Genre struct {
	gorm.Model

	Name  string `json:"name"`
	Books []Book `gorm:"many2many:book_genres;" json:"books"`
}

type BookGenre struct {
	BookId  uint `gorm:"primaryKey"`
	GenreId uint `gorm:"primaryKey"`
}
