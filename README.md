# Technical Task Hamilton Capital

This is a technical task from Hamilton Capital. I have names this app is "Hamilton PAY" and this is a Kotlin first project and I have followed the advanced Android development using MVVM design pattern, Dependancy injection (Koin), Kotlin Coroutine, Kotlin Flows, LiveData etc.

## App Details

Here are some Screenshots of the application:

<img src="https://user-images.githubusercontent.com/11981999/188101986-420cd6f4-5752-4f53-8b2e-dd1947e045bc.png" alt="" data-canonical-src="https://user-images.githubusercontent.com/11981999/188101986-420cd6f4-5752-4f53-8b2e-dd1947e045bc.png" width="200" height="400" /> <img src="https://user-images.githubusercontent.com/11981999/188100423-9ff6456f-746f-45ee-85f2-7d657f9c11aa.png" alt="" data-canonical-src="https://user-images.githubusercontent.com/11981999/188100423-9ff6456f-746f-45ee-85f2-7d657f9c11aa.png" width="200" height="400" /> <img src="https://user-images.githubusercontent.com/11981999/188102194-cccd4d1b-6899-409c-85bb-1e22dd562d67.png" alt="" data-canonical-src="https://user-images.githubusercontent.com/11981999/188102194-cccd4d1b-6899-409c-85bb-1e22dd562d67.png" width="200" height="400" /> <img src="https://user-images.githubusercontent.com/11981999/188100357-292b2921-b1a2-4810-b117-2b875453032b.png" alt="" data-canonical-src="https://user-images.githubusercontent.com/11981999/188100357-292b2921-b1a2-4810-b117-2b875453032b.png" width="200" height="400" />

### Features:
1. 100+ currencies list
2. 100+ currencies converter
3. Transaction time limit with 30 sec timer
4. Successfull transaction


### Used Technologies

I tried to build this application following these terms. which are:

- performance
- readability
- maintainability
- testability
- scalability
- simplicity

The tools I have used to gain the MVVM architecture pattern:

- MVVM :  MVVM architecure is followed for the code boilerplate. Where View, ViewModel, Repisitory are clearly used for maintailed the SOLID principle. (https://www.geeksforgeeks.org/mvvm-model-view-viewmodel-architecture-pattern-in-android/)
- Kotlin (https://kotlinlang.org/)
- Coroutine : To reduce the main thread task we can divide the task in many thread asychronously using the Kotlin Coroutine using viewmodel and lifecycle scope. (https://developer.android.com/kotlin/coroutines)
- Koin : KOIN is a ligh weight dependency injection which is written in pure Kotlin. Its really easy to learn and there is no generated code will be in KOIN used Android project. (https://insert-koin.io/)
- Kotlin Flows : In coroutines, a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value. (https://developer.android.com/kotlin/flow)
- LiveData : LiveData used in ViewModel to complete the timer task
- Retrofit : (https://square.github.io/retrofit/)
- BuildTypes: Build types used for defines the different build types like Development, Release and Stages
- ViewBinding: ViewBinding used for the view initialization

### Unit Test
For unit test I used Junit, where I tried two ways plain Unit test. For coverting the currencies with amount and ratio calculation I have written the test cases. Here is the test case result:

<img width="847" alt="Screenshot 2022-09-02 at 09 58 45" src="https://user-images.githubusercontent.com/11981999/188103961-e2dc6877-6bb9-42a4-a550-03def69a59d8.png">


#### API Specification

For this repository I have used a public currency coverter api. Here is the link: https://www.exchangerate-api.com/docs/java-currency-api

<img width="847" alt="Screenshot 2022-09-02 at 10 01 30" src="https://user-images.githubusercontent.com/11981999/188112495-90f0cbc6-b49f-4302-93f8-37f616bb4e65.png">

