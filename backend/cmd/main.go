package main

import (
	"book-store-server/database"
	"book-store-server/internals/routes"
	"fmt"
	"log"
	"net/http"
	"os"

	"github.com/joho/godotenv"
)

func main() {
	if err := godotenv.Load(".env"); err != nil {
		log.Println("Error loading app.env file: ", err)
	}

	database.InitDB()

	port := os.Getenv("PORT")
	if port == "" {
		port = "8080"
	}

	r := routes.SetupRoutes()

	fmt.Printf("Server started at :%s\n", port)
	log.Fatal(http.ListenAndServe(":"+port, r))
}
