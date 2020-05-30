package com.bc181.sentosa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_film";
    private final static String KEY_JUDUl = "Judul";
    private final static String KEY_TAHUN = "Tahun";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_PEMAIN = "Pemain";
    private final static String KEY_SIPNOSIS= "SIPNOSIS";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
    private Context context;


    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " +  TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUl + " TEXT, " + KEY_TAHUN + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_GENRE + " TEXT, "
                + KEY_PEMAIN + " TEXT, " + KEY_SIPNOSIS + " TEXT);";
        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public void tambahFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());


        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());

        db.insert(TABLE_FILM, null, cv);

    }

    public void editFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());


        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm(int idFilm) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm() {
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                }
                catch (ParseException er){
                    er.printStackTrace();
                }

                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6)
                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }
        return dataFilm;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db) {
        int idFilm = 0;
        Date tempDate = new Date();

        //menambah data Film 1
        try{
            tempDate = sdFormat.parse("2002");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film1 = new Film(
                idFilm,
                "After",
                tempDate,
                storeImageFile(R.drawable.film1),
                "Action, superhero",
                "-",
                "After merupakan sebuah film yang disutradarai oleh Jenny Gage.\n" +
                        "\n" +
                        "Film ini diperankan di antaranya oleh Josephine Langford, Hero Fiennes Tiffin, Khadijha Red Thunder.\n" +
                        "\n" +
                        "Film ini menceritakan tentang Tessa, seorang siswa baru yang baru masuk ke sebuah perguruan tinggi. Ia berkenalan dengan Steph yang menjadi teman sekamarnya di asrama kampus. Ia juga berkenalan dengan seorang pria bernama Hardin yang memiliki pergaulan yang jauh berbeda dari dirinya. \n" +
                        "\n" +
                        "Suatu hari ia sebelum mengenal Hardin, Tessa merupakan mahasiswa teladan. Namun dunianya berubah saat mengenal Hardin. Pria misterius yang justru menjadi daya tarik bagi Tessa. "
        );

        tambahFilm(film1, db);
        idFilm++;

        // Data Film ke 2
        try{
            tempDate = sdFormat.parse("2012");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film2 = new Film(
                idFilm,
                "Joker",
                tempDate,
                storeImageFile(R.drawable.film2),
                "Action, Superhero",
                "-",
                "Suatu hari, papan iklan Arthur dicuri paksa oleh segerombolan anak jalanan yang kemudian mengeroyokinya di sebuah lorong. Usai kejadian itu, seorang rekan kerjanya meminjamkan sepucuk pistol sebagai alat perlindungan diri.\n" +
                        "\n" +
                        "Sebuah kesalahan kecil yang dilakukan Arthur saat melakukan kunjungan sebagai badut ke sebuah rumah sakit anak-anak, membuatnya dipecat dari pekerjaan. Di saat yang hampir bersamaan, ia juga baru mengetahui kalau kantor layanan sosial tempatnya memperoleh obat telah ditutup.\n" +
                        "\n" +
                        "Dalam perjalanan pulang menggunakan kereta bawah tanah, Arthur dirundung tiga pebisnis muda Wall Street, sehingga ia menembak mati ketiganya dengan pistol yang ia pinjam itu.\n" +
                        "\n" +
                        "Arthur tidak menyadari pembunuhan itu akan memulai gerakan unjuk rasa terhadap orang kaya di kota itu dengan menggunakan topeng badut.\n" +
                        "\n" +
                        "Baca juga: Joaquin Phoenix Sebut Joker Sebagai Film yang Sulit\n" +
                        "\n" +
                        "Sementara kancah politik di kota Gotham, tempat kisah dari film ini bergulir, seorang pria bernama Thomas Wayne yang tak lain adalah ayah Bruce Waiyne yang kelak menjadi sosok Batman, mencalonkan diri sebagai wali kota karena merasa resah dengan kekacauan di kota itu yang tidak kunjung pulih saban waktunya.\n" +
                        "\n" +
                        "Di lain hari, Arthur mencoba peruntungan di sebuah pentas stand up comedy. Sayangnya, penampilannya malam itu begitu buruk lantaran ia tidak bisa berhenti tertawa di atas panggung.\n" +
                        "\n" +
                        "Seorang pembawa acara talk show populer di televisi, Murray Franklin, bahkan menayangkan video penampilan buruk Arthur secara langsung sebagai ejekan." );
        tambahFilm(film2, db);
        idFilm++;

        // Tambah Film 3

        try{
            tempDate = sdFormat.parse("1995");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film3 = new Film(
                idFilm,
                "Freedom",
                tempDate,
                storeImageFile(R.drawable.film3),
                "Action",
                "-",
                "FREEDOM Writers merupakan film yang diangkat dari kisah nyata perjuangan seorang guru di wilayah New Port Beach, Amerika Serikat dalam membangkitkan kembali semangat anak-anak didiknya untuk belajar. Dikisahkan, Erin Gruwell, seorang wanita idealis berpendidikan tinggi, datang ke Woodrow Wilson High School sebagai guru Bahasa Inggris untuk kelas khusus anak-anak korban perkelahian antargeng rasial.  Misi Erin sangat mulia, ingin memberikan pendidikan yang layak bagi anak-anak bermasalah yang bahkan guru yang lebih berpengalaman pun enggan mengajar mereka.\n" +
                        "Tapi kenyataan tidak selalu seperti yang dipikirkan Erin. Di hari pertamanya mengajar, ia baru menyadari bahwa perang antargeng yang terjadi di kota tersebut juga terbawa sampai ke dalam kelas. Di dalam kelas mereka duduk berkelompok menurut ras masing-masing. Tak ada seorang pun yang mau duduk di kelompok ras yang berbeda. Kesalahpahaman kecil yang terjadi di dalam kelas bisa memicu perkelahian antarras.");
        tambahFilm(film3, db);


    }

}