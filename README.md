# Build 
[![Build Status](https://travis-ci.org/ivzb/Achievers.svg?branch=master)](https://travis-ci.org/ivzb/Achievers)

# Workflow
## Categories
Each **Category** has single parent. **Categories** could be nested as much as we want. Currently they are implemented as *Agency List Model*. Initial query is built with all **Categories** whose parentId == null. It is not good idea to load whole branch, since it is quite heavy traversing operation. In our case we are loading only certain nodes (e.g. **Categories** whose parentId == 5). When user reaches **Category** leaf (that has no children), its **Achievements** are being loaded. **Category** cannot have both child **Categories** and **Achievements**.

## Achievements

**Achievements** are self-contained goals that offer new challenges, satisfy goal-oriented people, and allow others to see your accomplishments.

The application is covering every aspect of lifestyle, including world exploration, professions, personal development; from the purely whimsical to the truly epic, there is something for everyone. Some achievements come with in-game rewards such as titles. All of these rewards are purely cosmetic and just for fun, but you’ll certainly stand out when you proudly display them.

### Achievement types
They come in several varieties:
1. Single Action - require specific actions to unlock, such as getting a haircut or going to gym 5 days consequently.
2. Progressive - involve a progress bar and require you to do something several times — such as give out 10 hugs. Once you complete an achievement to collect 10, you might next be asked to collect 25.
3. Meta achievements - unlock when you complete other achievements

**Achievements** are displayed when **Category** leaf has been reached, through search or in users profiles.

## Evidences
**Evidence** could be reviewed inside a specific **Achievement**. An **Evidence** is a proof that user accomplished certain **Achievement**.

User may choose between LinearLayout and CardView layouts.

* CardView layout is simple list with multiple **Evidence** artifacts. They could be scrolled down. When 80% of them are reached, new ones are being loaded. User might swipe left/right for like/dislike each item. A button might be clicked to load comments. 

* Custom layout has better UX as the user interacts only with one **Evidence** at a time.
He might react to it, read/write comments or swipe left/right for navigation between Evidences.

## Involvements
1. Bronze
2. Silver
3. Gold
4. Platinum
5. Diamond

## Quests
Much of Achievers involves the completion of quests. Quests usually reward the player with some combination of experience points, items, and in-game money. Quests are linked by a common theme, with each consecutive quest triggered by the completion of the previous, forming a quest chain. Quests commonly involve gathering a certain number of resources, finding a difficult to locate object, speaking to various people, visiting specific locations, interacting with objects in the world.

### Quest types
1. World
2. Daily
3. Weekly
4. Monthly

## Rewards
// todo

### Reward types
1. Experience
2. Item
3. Title

# TODO
-1. Create missing remote data sources
0. Remove all singleton create methods, make them lazy
1. User - Authentication & Authorization
2. User profile
   1. Own achievements
   2. Own evidence
   3. Friend people
3. Privacy
   1. Public
   2. Protected
   3. Private
4. Reputation (achievement) 
5. Groups - achieve with friends/strangers
6. Local data source (Room)

# Dependencies
1) GSON - A Java serialization/deserialization library to convert Java Objects into JSON and back. [GitHub](https://github.com/google/gson)
2) OkHttp - HTTP & HTTP/2 client. [GitHub](https://github.com/square/okhttp)
3) Retrofit - Type-safe HTTP client on the top of OkHttp. [GitHub](https://github.com/square/retrofit)
4) Stetho - Sophisticated debug bridge, enabling the powerful Chrome Developer Tools. [GitHub](https://github.com/facebook/stetho)
5) Faker - Provides fake data, used while developing in order not to make server requests. [GitHub](https://github.com/thiagokimo/Faker)
6) Fresco - Powerful system for displaying images. [GitHub](https://github.com/facebook/fresco)
7) DialogPlus
8) Parceler - a code generation library that generates the Android Parcelable boilerplate source code. [GitHub](https://github.com/johncarl81/parceler)
9) Mockito
10) Android components (only Room for now)
11) More...
