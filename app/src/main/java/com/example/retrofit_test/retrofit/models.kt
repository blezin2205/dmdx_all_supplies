package com.example.retrofit_test.retrofit

data class Product(
    val id: Int,
    val name: String,
    val category: String,
//    val supplyLot: String,
    val ref: String?,
    val expiredDate: String,
    val dateCreated: String,
    val count: String,
    val countOnHold: String,
    val smn_code: String,
    val package_and_tests: String
)

//{
//    "id": 6,
//    "dateCreated": "17-02-2022",
//    "expiredDate": "30-04-2023",
//    "name": "TruCal U",
//    "ref": null,
//    "supplyLot": "31293",
//    "count": 1,
//    "countOnHold": 0,
//    "preCountOnHold": 0,
//    "general_supply": 1245,
//    "category": {
//    "id": 5,
//    "name": "DIASIS"
//}
//}

data class Category (
    val id: Int,
    val name: String
)

data class Products(
    val products: List<Product>
)

data class Supply(
    val id: Int,
    val name: String
)