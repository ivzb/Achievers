# Workflow
## Categories
Each **Category** has single parent. **Categories** could be nested as much as we want. Currently they are implemented as *Agency List Model*. Initial query is built with all **Categories** whose parentId == null. It is not good idea to load whole branch, since it is quite heavy traversing operation. In our case we are loading only certain nodes (e.g. **Categories** whose parentId == 5). When user reaches **Category** leaf (that has no children), its **Achievements** are being loaded. **Category** cannot have both child **Categories** and **Achievements**.

## Achievements

**Achievements** are self-contained goals that offer new challenges, satisfy goal-oriented people, and allow others to see your accomplishments.

The application is covering every aspect of lifestyle, including world exploration, professions, personal development; from the purely whimsical to the truly epic, there is something for everyone. Some achievements come with in-game rewards such as titles. All of these rewards are purely cosmetic and just for fun, but you’ll certainly stand out when you proudly display them.

They come in several varieties:
1. Single Action - require specific actions to unlock, such as getting a haircut or going to gym 5 days consequently.
2. Progressive - involve a progress bar and require you to do something several times — such as give out 10 hugs. Once you complete an achievement to collect 10, you might next be asked to collect 25.
4. Meta achievements - unlock when you complete other achievements

**Achievements** are displayed when **Category** leaf has been reached, throught search or in users profiles.

## Evidence
**Evidence** could be reviewed inside a specific **Achievement**. An **Evidence** is a proof that user accomplished certain **Achievement**.

User may choose between LinearLayout and CardView layouts.

* CardView layout is simple list with multiple **Evidence** artifacts. They could be scrolled down. When 80% of them are reached, new ones are being loaded. User might swipe left/right for like/dislike each item. A button might be clicked to load comments. 

* Custom layout has better UX as the user interacts only with one **Evidence** at a time.
He might swipe left for dislike current **Evidence** or opposite - swipe right to like it. After every interaction, next **Evidence** which he hasn't reviewed yet is being loaded.

# TODO
1. ScrollChildSwipeRefreshLayout - show user friendly message when there is no data in inner RecyclerView
   1. refresh correct category, now it is always root
2. AchievementDetailView/Evidence RecyclerView layout
   1. Audio support inside RecyclerView - possibly extend Toro
   2. Show Location as map
   3. Upload Evidence
3. User profile
4. Achievements structure
5. Quests - essential part
6. Groups - achieve something with friends/strangers
7. Use TDD

# Dependencies
1) GSON - A Java serialization/deserialization library to convert Java Objects into JSON and back. [GitHub](https://github.com/google/gson)
2) OkHttp - HTTP & HTTP/2 client. [GitHub](https://github.com/square/okhttp)
3) Retrofit - Type-safe HTTP client on the top of OkHttp. [GitHub](https://github.com/square/retrofit)
4) Stetho - Sophisticated debug bridge, enabling the powerful Chrome Developer Tools. [GitHub](https://github.com/facebook/stetho)
5) Faker - Provides fake data, used while developing in order not to make server requests. [GitHub](https://github.com/thiagokimo/Faker)
6) Toro - Video list auto playback made simple, specially built for RecyclerView. [GitHub](https://github.com/eneim/Toro)
7) Fresco - Powerful system for displaying images. [GitHub](https://github.com/facebook/fresco)
