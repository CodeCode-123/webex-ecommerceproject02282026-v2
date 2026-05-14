# webex-ecommerceproject02282026-v2

## Postman test cases:

### Category:

1. Name: CreateCategory, Method: POST, URL: http://localhost:8185/api/category/create, RequestBody:

```json
{
  "categoryName": "Pizza",
  "categoryDesc": "Cheese Pizza"
}
```

2. Name: CreateCategory, Method: POST, URL: http://localhost:8185/api/category/create, RequestBody:

```json
{
  "categoryName": "Burger",
  "categoryDesc": "Cheese Burger"
}
```

3. Name: GetAllCategories, (please use the token generated in the Login) Method: GET, URL: http://localhost:8185/api/category/
4. Name: GetCategoryById, Method: GET, URL: http://localhost:8185/api/category/1
5. Name: GetCategoryByName, Method: GET, URL: http://localhost:8185/api/category/search/P
6. Name: UpdateCategory, Method: PUT, URL: http://localhost:8185/api/category/edit, RequestBody:

```json
{
  "categoryId": 2,
  "categoryName": "Burger",
  "categoryDesc": "Double Cheese Burger"
}
```

7. Name: UpdateCategoryById, Method: PATCH, URL: http://localhost:8185/api/category/edit/2, RequestBody:

```json
{
  "categoryDesc": "Yummy Cheese Burger"
}
```

8. Name: DeleteCategoryById, Method: DELETE, URL: http://localhost:8185/api/category/delete/2

### Item:

1. Name: CreateItem, Method: POST, URL: http://localhost:8185/api/item/create, RequestBody:

```json
{
  "itemName": "Cheese Pizza",
  "itemPrice": 10,
  "category": {
    "categoryId": 1
  }
}
```

2. Name: CreateItem, Method: POST, URL: http://localhost:8185/api/item/create, RequestBody:

```json
{
  "itemName": "Cheese Burger",
  "itemPrice": 6,
  "category": {
    "categoryId": 2
  }
}
```

3. Name: GetAllItems, Method: GET, URL: http://localhost:8185/api/item/
4. Name: GetItemById, Method: GET, URL: http://localhost:8185/api/item/1
5. Name: UpdateItem, Method: PUT, URL: http://localhost:8185/api/item/edit, RequestBody:

```json
{
  "category": {
    "categoryDesc": "Cheese Burger",
    "categoryId": 2,
    "categoryName": "Burger"
  },
  "imageData": null,
  "itemId": 2,
  "itemName": "Cheese Burger",
  "itemPrice": 8
}
```

6. Name: UpdateItemById, (update itemName), Method: PATCH, URL: http://localhost:8185/api/item/edit/2, RequestBody:

```json
{
  "itemName": "Double Cheese Burger"
}
```

7. Name: UpdateItemById, (update category by categoryId), Method: PATCH, URL: http://localhost:8185/api/item/edit/1, RequestBody:

```json
{
  "category": {
    "categoryId": 2
  }
}
```

8. Name: DeleteItem, Method: DELETE, URL: http://localhost:8185/api/item/delete/2

### Users:

1. Name: CreateUsers, Method: POST, URL: http://localhost:8185/api/users/create, RequestBody:

```json
{
  "firstName": "test",
  "lastName": "test",
  "country": "USA",
  "gender": "Male",
  "languages": ["C#", "Java"],
  "emailId": "test@abc.com",
  "password": "1234",
  "role": "Admin"
}
```

2. Name: CreateUsers, Method: POST, URL: http://localhost:8185/api/users/create, RequestBody:

```json
{
  "firstName": "test2",
  "lastName": "test2",
  "country": "USA",
  "gender": "Male",
  "languages": ["C#", "Java"],
  "emailId": "test2@abc.com",
  "password": "1234",
  "role": "Admin"
}
```

3. Name: GetAllUsers, Method: GET, URL: http://localhost:8185/api/users/
4. Name: GetUsersById, Method: GET, URL: http://localhost:8185/api/users/1
5. Name: Login, Method: POST, URL: http://localhost:8185/api/users/login?emailId=test@abc.com&password=1234
6. Name: GetUserByEmailId, Method: GET, URL: http://localhost:8185/api/users/search/test@abc.com
7. Name: UpdateUsers, Method: PUT, URL: http://localhost:8185/api/users/edit, RequestBody:

```json
{
  "usersId": 1,
  "firstName": "test edit",
  "lastName": "test edit",
  "country": "USA",
  "gender": "Male",
  "languages": ["C#", "Java"],
  "emailId": "test@abc.com",
  "password": "1234",
  "role": "Admin"
}
```

8. Name: UpdateUsersById, Method: PATCH, URL: http://localhost:8185/api/users/edit/1, RequestBody:

```json
{
  "firstName": "test edit 2",
  "lastName": "test edit 2"
}
```

9. Name: DeleteUsers, Method: DELETE, URL: http://localhost:8185/api/users/delete/2

### ItemOrderDetails:

1. Name: CreateOrderDetails, Method: POST, URL: http://localhost:8185/api/orderdetails/create, RequestBody:

```json
{
  "item": {
    "itemId": 1
  },
  "qty": 1,
  "itemOrder": {
    "orderId": 0
  }
}
```

2. Name: CreateItemOrderDetails, (created an itemOrderDetails with another item) Method: POST, URL: http://localhost:8185/api/orderdetails/create, RequestBody:

```json
{
  "item": {
    "itemId": 2
  },
  "qty": 2
}
```

3. Name: GetAllItemOrderDetails, Method: GET, URL: http://localhost:8185/api/orderdetails/
4. Name: GetItemOrderDetailsById, Method: GET, URL: http://localhost:8185/api/orderdetails/1
5. Name: UpdateItemOrderDetails, Method: PUT, URL: http://localhost:8185/api/orderdetails/edit, RequestBody:

```json
{
  "item": {
    "category": {
      "categoryDesc": "Cheese Pizza",
      "categoryId": 1,
      "categoryName": "Pizza"
    },
    "imageData": null,
    "itemId": 1,
    "itemName": "Cheese Pizza",
    "itemPrice": 10.0
  },
  "itemOrderDetailsId": 1,
  "itemValue": 0,
  "qty": 2
}
```

6. Name: UpdateItemOrderDetailsById, (only update qty), Method: PATCH, URL: http://localhost:8185/api/orderdetails/edit/2, RequestBody:

```json
{
  "qty": 5
}
```

7. Name: DeleteOrderDetailsById, Method: DELETE, URL: http://localhost:8185/api/orderdetails/delete/2

### ItemOrders:

1. Name: PlaceOrders, (ensure that there is a user with id=1 and an item order details with itemOrderDetailsId=1 in the database, get the users' information and item order details' information during retrieving), Method: POST, URL: http://localhost:8185/api/orders/placeorder, RequestBody:

```json
{
  "users": {
    "usersId": 1
  },
  "cartItems": {
    "itemOrderDetailsList": [
      {
        "itemOrderDetailsId": 1
      }
    ]
  }
}
```

2. Name: PlaceOrders, (with multiple itemOrderDetails), Method: POST, URL: http://localhost:8185/api/orders/placeorder, RequestBody:

```json
{
  "users": {
    "userId": 1
  },
  "itemOrderDetailsList": [
    {
      "itemOrderDetailsId": 1
    },
    {
      "itemOrderDetailsId": 2
    }
  ]
}
```

3. Name: GetAllItemOrders, Method: GET, URL: http://localhost:8185/api/orders/
4. Name: GetItemOrdersById, Method: GET, URL: http://localhost:8185/api/orders/1
5. Name: DeleteItemOrdersById, Method: DELETE, URL: http://localhost:8185/api/orders/delete/2
