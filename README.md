# Workflow
## Categories
Each **Category** has single parent. **Categories** could be nested as much as we want. Currently they are implemented as *Agency List Model*. Initial query is built with all **Categories** whose parentId == null. It is not good idea to load whole branch, since it is quite heavy traversing operation. In our case we are loading only certain nodes (e.g. **Categories** whose parentId == 5). When user reaches **Category** leaf (that has no children), its **Achievements** are being loaded. **Category** cannot have both child **Categories** and **Achievements**.

## Achievements

## Evidence
**Evidence** could be reviewed inside a specific **Achievement**. An **Evidence** is a proof that user accomplished certain **Achievement**.

User may choose between LinearLayout and CardView layouts.

* CardView layout is simple list with multiple **Evidence** artifacts. They could be scrolled down. When 80% of them are reached, new ones are being loaded. User might swipe left/right for like/dislike each item. A button might be clicked to load comments. 

* Custom layout has better UX as the user interacts only with one **Evidence** at a time.
He might swipe left for dislike current **Evidence** or opposite - swipe right to like it. After every interaction, next **Evidence** which he hasn't reviewed yet is being loaded.

# TODO
1) ScrollChildSwipeRefreshLayout - show user friendly message when there is no data in inner RecyclerView
2) Migrate to Master/Detail views
3) Create back-end service
4) [Work In Progress] AchievementDetailView/Evidence RecyclerView layout
