package com.pemmob.nadin.data.dummy

import com.pemmob.nadin.data.model.Category
import com.pemmob.nadin.data.model.Product

object DummyData {
    val categories = listOf(
        Category(
            id = 1,
            name = "Makanan",
            description = "Olahan makanan khas Purbalingga",
            products_count = 5
        ),
        Category(
            id = 2,
            name = "Minuman",
            description = "Minuman segar dan hangat",
            products_count = 3
        ),
        Category(
            id = 3,
            name = "Kerajinan",
            description = "Kerajinan tangan lokal",
            products_count = 4
        )
    )

    val products = listOf(
        Product(
            id = 1,
            category_id = 1,
            category = categories[0],
            name = "Kripik Tempe Purbalingga",
            description = "Kripik tempe renyah khas Purbalingga buatan UMKM lokal.",
            price = 15000.0,
            stock = 20,
            img = "dummy_product"
        ),
        Product(
            id = 2,
            category_id = 2,
            category = categories[1],
            name = "Es Cendol Dawet",
            description = "Es cendol segar manis gula jawa asli.",
            price = 8000.0,
            stock = 15,
            img = "dummy_product"
        ),
        Product(
            id = 3,
            category_id = 3,
            category = categories[2],
            name = "Sapu Glagah Arca",
            description = "Sapu kerajinan tradisional kualitas ekspor.",
            price = 25000.0,
            stock = 10,
            img = "dummy_product"
        )
    )
}
