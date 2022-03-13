# Todo List 
A todo list android application shipped with next features:
  * can search for Task
  * can add New Task for Your List 
  * can modifiy Existing Task
  * can save all data Local in your device 
  * can filter By ( name - created at)
  * can delete task / all completed Task

# Screenshots 

<img src="https://user-images.githubusercontent.com/38116813/157339663-dcb2c21a-20d6-4b8e-88f2-755f39860262.jpg" width="20%"></img> <img src="https://user-images.githubusercontent.com/38116813/157340720-62f5b086-9b79-48c8-84f4-7a20fbdd5776.jpg" width="20%"></img> <img src="https://user-images.githubusercontent.com/38116813/157340786-9ddecfb7-a4d7-469e-8123-c53148d36059.jpg" width="20%"></img> <img src="https://user-images.githubusercontent.com/38116813/157340932-00064da9-ed64-45a2-9b37-5137c9b99b32.jpg" width="20%"></img> 


# Architecture
* Clean Architecture: 3 Layers (Presentation, Domain, Data) Layers.
  * Presentation Layer:
     * mvvm Presentation Architecture Pattern.
     * view Model 
  * Domain Layer:
      * UseCases.
      * Repository Interfaces.
  * Data Layer:
     * Repository Implementation.
     * Models.
     * Database: Room Database .
# Tools 
* hilt for di (Dependency Injection).
* Kotlin Coroutines.
* kotlin flow and channel 
* DataStore preferences
* navigation component  
* Dialog fragment 

