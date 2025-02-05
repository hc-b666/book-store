<template>
  <div class="w-full">
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>Title</TableHead>
          <TableHead>Author</TableHead>
          <TableHead>Genre</TableHead>
          <TableHead>Publisher</TableHead>
          <TableHead>Published Date</TableHead>
          <TableHead>ISBN</TableHead>
          <TableHead>Pages</TableHead>
          <TableHead>Stock</TableHead>
          <TableHead>Price</TableHead>
          <TableHead>Actions</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        <TableRow v-for="book in books" :key="book.isbn">
          <TableCell>{{ book.title }}</TableCell>
          <TableCell>{{ book.author }}</TableCell>
          <TableCell>{{ book.genre }}</TableCell>
          <TableCell>{{ book.publisher }}</TableCell>
          <TableCell>{{ formatDate(book.published_date) }}</TableCell>
          <TableCell>{{ book.isbn }}</TableCell>
          <TableCell>{{ book.page_count }}</TableCell>
          <TableCell>
            <Badge :variant="getStockVariant(book.stock)">
              {{ book.stock }}
            </Badge>
          </TableCell>
          <TableCell>${{ formatPrice(book.price) }}</TableCell>
          <TableCell>
            <div class="flex items-center gap-2">
              <Button variant="outline" size="sm" @click="editBook(book)">
                Edit
              </Button>
              <Button variant="destructive" size="sm" @click="deleteBook(book)">
                Delete
              </Button>
            </div>
          </TableCell>
        </TableRow>
        <TableRow v-if="books.length === 0">
          <TableCell colspan="10" class="text-center h-24">
            No books found.
          </TableCell>
        </TableRow>
      </TableBody>
    </Table>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'

const books = ref([
  {
    title: "Sample Book",
    author: "John Doe",
    genre: "Fiction",
    description: "A sample book description",
    price: 29.99,
    page_count: 300,
    stock: 15,
    publisher: "Sample Publisher",
    published_date: "2024-02-05",
    isbn: "978-0123456789"
  }
])

const formatDate = (date) => {
  return new Date(date).toLocaleDateString()
}

const formatPrice = (price) => {
  return Number(price).toFixed(2)
}

const getStockVariant = (stock) => {
  if (stock === 0) return "destructive"
  if (stock < 5) return "warning"
  return "success"
}
</script>