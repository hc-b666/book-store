<script setup lang="ts">
import { ref } from 'vue'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Textarea } from '@/components/ui/textarea'
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'

const formData = ref({
  title: "",
  author: "",
  genre: "",
  description: "",
  price: "",
  page_count: "",
  stock: "",
  publisher: "",
  published_date: "",
  isbn: "",
})

const handleSubmit = () => {
  console.log('Form Data:', formData.value)
}

</script>

<template>
  <form @submit.prevent="handleSubmit" class="min-w-[480px] space-y-6 max-w-xl mx-auto p-6">
    <div class="space-y-2">
      <Label for="title">Book Title</Label>
      <Input id="title" v-model="formData.title" placeholder="Enter book title" required />
    </div>
    <div class="space-y-2">
      <Label for="author">Author</Label>
      <Input id="author" v-model="formData.author" placeholder="Enter author name" required />
    </div>
    <div class="space-y-2">
      <Label for="genre">Genre</Label>
      <Select v-model="formData.genre">
        <SelectTrigger>
          <SelectValue placeholder="Select genre" />
        </SelectTrigger>
        <SelectContent>
          <SelectItem value="fiction">Fiction</SelectItem>
          <SelectItem value="non-fiction">Non-Fiction</SelectItem>
          <SelectItem value="science">Science</SelectItem>
          <SelectItem value="history">History</SelectItem>
        </SelectContent>
      </Select>
    </div>
    <div class="space-y-2">
      <Label for="description">Description</Label>
      <Textarea id="description" v-model="formData.description" placeholder="Enter book description" rows="4" />
    </div>
    <div class="space-y-2">
      <Label for="price">Price</Label>
      <Input id="price" type="number" v-model="formData.price" placeholder="Enter price" step="0.01" required />
    </div>
    <div class="space-y-2">
      <Label for="page_count">Page Count</Label>
      <Input id="page_count" v-model="formData.page_count" placeholder="Enter author name" required />
    </div>
    <div class="space-y-2">
      <Label for="stock">Stock</Label>
      <Input id="stock" v-model="formData.stock" placeholder="Enter author name" required />
    </div>
    <div class="space-y-2">
      <Label for="publisher">Publisher</Label>
      <Input id="publisher" v-model="formData.publisher" placeholder="Enter author name" required />
    </div>
    <div class="space-y-2">
      <Label for="published_date">Published Date</Label>
      <Input id="published_date" v-model="formData.published_date" placeholder="Enter author name" required />
    </div>
    <div class="space-y-2">
      <Label for="isbn">ISBN</Label>
      <Input id="isbn" v-model="formData.isbn" placeholder="Enter author name" required />
    </div>
    <Button type="submit" class="w-full">Create</Button>
  </form>
</template>
