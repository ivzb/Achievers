package com.achievers.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract class for interacting with {@link AchieversProvider}. Unless otherwise noted, all
 * time-based fields are milliseconds since epoch and can be compared against
 * {@link System#currentTimeMillis()}.
 * <p>
 * The backing {@link android.content.ContentProvider} assumes that {@link android.net.Uri}
 * are generated using stronger {@link java.lang.String} identifiers, instead of
 * {@code int} {@link android.provider.BaseColumns#_ID} values, which are prone to shuffle during
 * sync.
 */
public class AchieversContract {

    public static final String CONTENT_TYPE_APP_BASE = "achievers.";

    public static final String CONTENT_TYPE_BASE = "vnd.android.cursor.dir/vnd."
            + CONTENT_TYPE_APP_BASE;

    public static final String CONTENT_ITEM_TYPE_BASE = "vnd.android.cursor.item/vnd."
            + CONTENT_TYPE_APP_BASE;

    public static final String CONTENT_AUTHORITY = "com.achievers";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private AchieversContract() {

    }

    public interface CategoriesColumns {
        String CATEGORY_ID = "category_id";
        String CATEGORY_TITLE = "category_title";
        String CATEGORY_DESCRIPTION = "category_description";
        String CATEGORY_IMAGE_URL = "category_image_url";
        String CATEGORY_PARENT_ID = "category_parent_id";
    }

    interface AchievementsColumns {
        String ACHIEVEMENT_ID = "achievement_id";
        String ACHIEVEMENT_TITLE = "achievement_title";
        String ACHIEVEMENT_DESCRIPTION = "achievement_description";
        String ACHIEVEMENT_IMAGE_URL = "achievement_image_url";
        String ACHIEVEMENT_CATEGORY_ID = "achievement_category_id";
        String ACHIEVEMENT_INVOLVEMENT = "achievement_involvement";
        String ACHIEVEMENT_AUTHOR_ID = "achievement_author_id";
    }

    interface EvidenceColumns {
        String EVIDENCE_ID = "evidence_id";
        String EVIDENCE_TITLE = "evidence_title";
        String EVIDENCE_TYPE = "evidence_type";
        String EVIDENCE_URL = "evidence_url";
        String EVIDENCE_ACHIEVEMENT_ID = "evidence_achievement_id";
        String EVIDENCE_AUTHOR_ID = "evidence_author_id";
    }

    public static String makeContentType(String id) {
        if (id != null) {
            return CONTENT_TYPE_BASE + id;
        } else {
            return null;
        }
    }

    public static String makeContentItemType(String id) {
        if (id != null) {
            return CONTENT_ITEM_TYPE_BASE + id;
        } else {
            return null;
        }
    }

    /**
     * Tags represent Session classifications. A session can have many tags. Tags can indicate,
     * for example, what product a session pertains to (Android, Chrome, ...), what type
     * of session it is (session, codelab, office hours, ...) and what overall event theme
     * it falls under (Design, Develop, Distribute), amongst others.
     */
    public static class Categories implements CategoriesColumns, BaseColumns {

        public static final String TABLE_NAME = "categories";

        private static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        static final String CONTENT_TYPE_ID = "category";

        public static String[] COLUMNS = new String[] {
            AchieversContract.Categories.CATEGORY_ID,
            AchieversContract.Categories.CATEGORY_TITLE,
            AchieversContract.Categories.CATEGORY_DESCRIPTION,
            AchieversContract.Categories.CATEGORY_IMAGE_URL,
            AchieversContract.Categories.CATEGORY_PARENT_ID
        };

        public interface INDEXES {
            int CATEGORY_ID = 0;
            int CATEGORY_TITLE = 1;
            int CATEGORY_DESCRIPTION = 2;
            int CATEGORY_IMAGE_URL = 3;
            int CATEGORY_PARENT_ID = 3;
        }

        /**
         * Build {@link Uri} that references all tags.
         */
        public static Uri buildCategoriesUri() {
            return CONTENT_URI;
        }

        /** Build a {@link Uri} that references a given tag. */
        public static Uri buildCategoriesByParentUri(String parentId) {
            return CONTENT_URI.buildUpon().appendPath(parentId).build();
        }

        /** Read {@link #CATEGORY_ID} from {@link Categories} {@link Uri}. */
        public static String getCategoryParentId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class Achievements implements AchievementsColumns, BaseColumns {

        public static final String TABLE_NAME = "achievements";

        public static final String ACHIEVEMENT_INVOLVEMENT_BRONZE = "bronze";

        public static final String ACHIEVEMENT_INVOLVEMENT_SILVER = "silver";

        public static final String ACHIEVEMENT_INVOLVEMENT_GOLD = "gold";

        public static final String ACHIEVEMENT_INVOLVEMENT_PLATINUM = "platinum";

        public static final String ACHIEVEMENT_INVOLVEMENT_DIAMOND = "diamond";

        public static final boolean isValidAchievementInvolvement(String involvement) {
            switch (involvement) {
                case ACHIEVEMENT_INVOLVEMENT_BRONZE:
                case ACHIEVEMENT_INVOLVEMENT_SILVER:
                case ACHIEVEMENT_INVOLVEMENT_GOLD:
                case ACHIEVEMENT_INVOLVEMENT_PLATINUM:
                case ACHIEVEMENT_INVOLVEMENT_DIAMOND:
                    return true;
            }

            return false;
        }

        private static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        static final String CONTENT_TYPE_ID = "achievement";

        /**
         * Build {@link Uri} that references all tags.
         */
        public static Uri buildAchievementsUri() {
            return CONTENT_URI;
        }

        /** Build {@link Uri} for requested {@link #ACHIEVEMENT_ID}. */
        public static Uri buildAchievementUri(String achievementId) {
            return CONTENT_URI.buildUpon().appendPath(achievementId).build();
        }

        /** Read {@link #ACHIEVEMENT_ID} from {@link Achievements} {@link Uri}. */
        public static String getAchievementId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class Evidence implements EvidenceColumns, BaseColumns {

        public static final String TABLE_NAME = "evidence";

        public static final String EVIDENCE_TYPE_IMAGE = "image";

        public static final String EVIDENCE_TYPE_VIDEO = "video";

        public static final String EVIDENCE_TYPE_VOICE = "voice";

        public static final String EVIDENCE_TYPE_LOCATION = "location";

        public static final boolean isValidEvidenceType(String type) {
            switch (type) {
                case EVIDENCE_TYPE_IMAGE:
                case EVIDENCE_TYPE_VIDEO:
                case EVIDENCE_TYPE_VOICE:
                case EVIDENCE_TYPE_LOCATION:
                    return true;
            }

            return false;
        }

        private static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        static final String CONTENT_TYPE_ID = "evidence";

        /**
         * Build {@link Uri} that references all tags.
         */
        public static Uri buildEvidenceUri() {
            return CONTENT_URI;
        }

        /** Build {@link Uri} for requested {@link #EVIDENCE_ID}. */
        public static Uri buildEvidenceUri(String evidenceId) {
            return CONTENT_URI.buildUpon().appendPath(evidenceId).build();
        }

        /** Read {@link #EVIDENCE_ID} from {@link Achievements} {@link Uri}. */
        public static String getEvidenceId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}
