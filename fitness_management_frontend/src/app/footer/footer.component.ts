import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  template: `
    <footer class="bg-gray-900 text-white py-12">
      <div class="container mx-auto px-4">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-12"> <!-- Augmentation du gap -->
          <div class="col-span-2"> <!-- Augmentation de la largeur de cette colonne -->
            <h2 class="text-2xl font-bold mb-4">Stay in Touch</h2>
            <form class="flex flex-col sm:flex-row">
              <input
                type="email"
                placeholder="Your email..."
                class="flex-grow bg-white text-gray-900 px-4 py-2 mb-2 sm:mb-0 sm:mr-2 rounded-md focus:outline-none"
              />
              <button
                type="submit"
                class="bg-teal-500 text-white px-4 py-2 rounded-md hover:bg-teal-600 transition duration-300"
              >
                SUBSCRIBE NOW
              </button>
            </form>
          </div>
          <div>
            <h3 class="text-xl font-semibold mb-4">TOFIT</h3>
            <p class="text-gray-400">
              Whether you're curling up with a good book or hosting a festive holiday gathering, a fireplace makes
              every moment feel special and warm.
            </p>
          </div>
          <div>
            <h3 class="text-xl font-semibold mb-4">Explore</h3>
            <ul class="space-y-2">
              <li><a href="#" class="text-gray-400 hover:text-teal-500">Articles</a></li>
              <li><a href="#" class="text-gray-400 hover:text-teal-500">FAQ</a></li>
              <li><a href="#" class="text-gray-400 hover:text-teal-500">Contacts</a></li>
              <li><a href="#" class="text-gray-400 hover:text-teal-500">Testimonials</a></li>
              <li><a href="#" class="text-gray-400 hover:text-teal-500">Products</a></li>
              <li><a href="#" class="text-gray-400 hover:text-teal-500">Gallery</a></li>
              <li><a href="#" class="text-gray-400 hover:text-teal-500">About Us</a></li>
              <li><a href="#" class="text-gray-400 hover:text-teal-500">Blog</a></li>
            </ul>
          </div>
          <div>
            <h3 class="text-xl font-semibold mb-4">Contact</h3>
            <ul class="space-y-2 text-gray-400">
              <li>13 Division st, New York, 16004</li>
              <li>0-900-856-05-39</li>
              <li>Mon-Fri: 8am - 4pm</li>
            </ul>
          </div>
        </div>
        <div class="mt-8 pt-8 border-t border-gray-800 text-center text-gray-400">
          <p>&copy; All Rights Reserved - 2024 - Purchase</p>
        </div>
      </div>
    </footer>
  `,
  styles: [],
})
export class FooterComponent {}
