package com.teamplusplus.codeforces.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class TitleBlogEntriesIdsDBHelper extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TitleBlogEntriesDB";

    // Contacts table name
    private static final String TABLE_CONTACTS = "blogEntriesTable";

    private static final String KEY_id = "id";

    public TitleBlogEntriesIdsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void add(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_id, id);
        db.insert(TABLE_CONTACTS, null, values);
    }

    public boolean has(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, null, KEY_id + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        boolean flag = false;

        if (cursor.getCount() != 0) {
            flag = true;
            cursor.moveToFirst();

            cursor.close();
        }

        return flag;
    }

    // Getting All Contacts
    public List<Integer> getAllBlogEntry() {
        List<Integer> intList = new ArrayList<>();
        // Select All Query

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, null, null, null, null, null, KEY_id + " DESC");

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                intList.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return intList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_id + " INTEGER " + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        int array[] = {43974, 43953, 43860, 43848, 43821,
                43835, 43797, 43615, 43536, 43490, 43469, 43460, 43350, 43421, 43375, 43212, 24159, 23623, 23604, 23572,
                23515, 23219, 23358, 23309, 23258, 23240, 23147, 23084, 23083, 22950, 22829, 22925, 22889, 22834, 22726,
                22691, 22657, 22619, 22618, 22466, 22389, 22399, 22386, 22335, 22304, 22295, 22300, 22262, 22255, 22251,
                22233, 22175, 22173, 22055, 22002, 21912, 21892, 21860, 21858, 21794, 21795, 21490, 21662, 21585, 21588,
                21513, 21565, 21496, 21464, 21371, 21284, 20762, 21185, 21071, 20989, 20913, 20889, 20739, 20729, 20694,
                20657, 20638, 20629, 20548, 20522, 20376, 20334, 20276, 20225, 20072, 19957, 20020, 19863, 19774, 19753,
                19681, 19590, 19478, 19426, 19425, 19331, 19289, 19229, 19173, 19010, 18958, 18972, 18896, 18825, 18698,
                18659, 18473, 18426, 18348, 18327, 18303, 18306, 18308, 18274, 18046, 18051, 18021, 18031, 17983, 17967,
                17968, 17955, 17947, 17923, 17904, 17882, 17842, 17828, 17823, 17755, 17690, 17648, 17636, 17548, 17465,
                17446, 17371, 17366, 17360, 17330, 16744, 17302, 17154, 17245, 17138, 17113, 17051, 16996, 16911, 16707,
                16662, 16594, 16592, 16446, 16365, 16206, 16140, 16147, 16124, 16083, 16035, 15981, 15930, 15090, 15345,
                15869, 15842, 15829, 15739, 15729, 15725, 15701, 15643, 15554, 15547, 15499, 15492, 15473, 15465, 15416,
                15336, 15282, 15268, 15197, 15157, 15134, 14959, 14945, 14893, 14826, 14773, 14776, 14775, 14753, 14711,
                14695, 14672, 14611, 14591, 14580, 14184, 14370, 14509, 14415, 14420, 14368, 14387, 14343, 14330, 14291,
                14282, 14228, 14171, 14185, 14086, 14077, 14071, 13997, 13950, 13962, 13929, 13826, 13836, 13831, 13828,
                13797, 13777, 13750, 13746, 13745, 13742, 13663, 13598, 13542, 13508, 13437, 13417, 13394, 13346, 13319,
                13247, 13141, 13095, 13111, 13088, 13063, 13004, 12923, 12816, 12789, 12704, 12695, 12683, 12642, 12605,
                12602, 12584, 12518, 12526, 12520, 12490, 12450, 12404, 12378, 12353, 12307, 12298, 12273, 12254, 12023,
                11969, 11917, 11842, 11784, 11746, 11632, 11646, 11623, 11458, 11450, 11400, 11391, 11322, 11309, 11256,
                11153, 11039, 11011, 10959, 10786, 10730, 10683, 10605, 10569, 10512, 10448, 10423, 10422, 10334, 10193,
                10148, 10161, 10138, 10099, 10034, 10024, 10003, 9905, 9907, 9871, 9854, 9784, 9738, 9733, 9715, 9699,
                9687, 9659, 9625, 9550, 9554, 9533, 9526, 9522, 9465, 9418, 9391, 9351, 9331, 9326, 9305, 9208, 9199,
                9178, 9139, 9138, 9130, 9075, 9056, 9044, 9003, 9034, 8985, 8963, 8953, 8896, 8889, 8851, 8808, 8810,
                8795, 8790, 8748, 8721, 8629, 8615, 8524, 8506, 8456, 8453, 8457, 8424, 8402, 8386, 8344, 8341, 8304,
                8297, 8248, 8254, 8234, 8163, 8168, 8063, 8051, 7979, 7862, 7937, 7890, 7900, 7894, 7881, 7769, 7803,
                7773, 7770, 7758, 7743, 7736, 7708, 7693, 7682, 7499, 7628, 7613, 7573, 7557, 7547, 7519, 7493, 7476,
                7479, 7461, 7460, 7390, 7361, 7336, 7321, 7271, 7075, 7218, 7216, 7204, 7205, 7062, 7104, 7087, 7027,
                6954, 6870, 6856, 6806, 6777, 6739, 6736, 6709, 6706, 6704, 6693, 6677, 6659, 6648, 6615, 6576, 6537,
                6494, 6483, 6454, 6401, 6416, 6392, 6389, 6353, 6290, 6278, 6268, 6253, 6199, 6153, 6062, 6038, 6010,
                6008, 5966, 5947, 5929, 5912, 5871, 5826, 5746, 5741, 5722, 5681, 5639, 5640, 5586, 5531, 5510, 5483,
                5443, 5432, 5409, 5396, 5357, 5317, 5305, 5287, 5248, 5211, 5167, 5156, 5083, 5061, 5017, 4965, 4924,
                4885, 4884, 4879, 4878, 4874, 4848, 4831, 4818, 4796, 4765, 4757, 4708, 4697, 4696, 4671, 4632, 4635,
                4627, 4619, 4577, 4554, 4550, 4534, 4520, 4482, 4468, 4459, 4428, 4425, 4421, 4403, 4390, 4375, 4370,
                4351, 4330, 4311, 4302, 4290, 4262, 4255, 4230, 4225, 4214, 4207, 4185, 4178, 4148, 4171, 4142, 4139,
                4133, 4120, 4092, 4072, 4042, 4012, 3994, 3977, 3973, 3958, 3948, 3929, 3926, 3923, 3905, 3899, 3864,
                3859, 3857, 3819, 3812, 3795, 3780, 3503, 3756, 3768, 3732, 3734, 3702, 3688, 3676, 3649, 3624, 3570,
                3531, 3524, 3504, 3475, 3461, 3457, 3318, 3317, 3353, 3336, 3302, 3292, 3289, 3284, 3262, 3243, 3226,
                3214, 3193, 3094, 3077, 3071, 3064, 3033, 2968, 2948, 2923, 2886, 2856, 2847, 2799, 2788, 2746, 2740,
                2726, 2725, 2707, 2700, 2662, 2625, 2607, 2576, 2543, 2511, 2484, 2446, 2405, 2384, 2359, 2346, 2319,
                2295, 2289, 2255, 2205, 2179, 2155, 2129, 2115, 2084, 2078, 2070, 2063, 2049, 2031, 2026, 2008, 1947,
                1936, 1886, 1858, 1830, 1828, 1808, 1775, 1767, 1730, 1713, 1697, 1690, 1679, 1637, 1623, 1601, 1592,
                1570, 1535, 1539, 1475, 1455, 1448, 1430, 1422, 1383, 1380, 1356, 1337, 1316, 1309, 1272, 1197, 1165,
                1145, 1107, 1101, 1083, 1062, 1058, 1048, 1040, 1030, 1028, 1006, 967, 985, 969, 960, 941, 927, 912,
                900, 888, 882, 864, 861, 843, 823, 820, 819, 800, 792, 781, 779, 764, 763, 753, 746, 729, 726, 714,
                700, 704, 692, 684, 674, 652, 597, 573, 557, 553, 528, 507, 505, 496, 484, 456, 481, 463, 462, 451,
                447, 427, 422, 408, 403, 382, 364, 355, 339, 331, 298, 283, 276, 267, 258, 239, 220, 211, 196, 178,
                172, 158, 155, 147, 141, 130, 126, 123, 120, 109, 107, 102, 97, 85, 84, 81, 79, 73, 69, 59, 56, 50,
                49};
        ContentValues values;

        assert array != null;
        for (int num : array) {
            values = new ContentValues();
            values.put(KEY_id, num);

            db.insert(TABLE_CONTACTS, null, values);
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }


}
