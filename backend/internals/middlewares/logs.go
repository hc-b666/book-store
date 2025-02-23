package middlewares

import (
	"log"
	"net/http"
	"time"

	"github.com/gorilla/mux"
)

func LoggingMiddleware(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		startTime := time.Now()

		if route := mux.CurrentRoute(r); route != nil {
			pathTemplate, _ := route.GetPathTemplate()
			log.Printf(
				"[%s] %s %s (Route: %s) - Client: %s",
				startTime.Format("2006-01-02 15:04:05"),
				r.Method,
				r.RequestURI,
				pathTemplate,
				r.RemoteAddr,
			)
		} else {
			log.Printf(
				"[%s] %s %s - Client: %s",
				startTime.Format("2006-01-02 15:04:05"),
				r.Method,
				r.RequestURI,
				r.RemoteAddr,
			)
		}

		next.ServeHTTP(w, r)

		duration := time.Since(startTime)
		log.Printf("Request processed in %v\n", duration)
	})
}
