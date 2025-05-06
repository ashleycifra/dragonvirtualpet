package ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler (context: Context) : SQLiteOpenHelper(context, DATABASENAME, null, DATABASEVERSION){
    companion object{
        private val DATABASEVERSION = 1
        private val DATABASENAME = "PopoyDatabase"

        const val TABLE_PINFO_TABLE = "PetInfo_Table"
        const val TABLE_PINFO_NAME = "PetInfo_Name"
        const val TABLE_PINFO_AGE = "PetInfo_Age"
        const val TABLE_PINFO_LEVEL = "PetInfo_Level"
        const val TABLE_PINFO_GENDER = "PetInfo_Gender"
        const val TABLE_PINFO_STAGE = "PetInfo_Stage"
        const val TABLE_PINFO_MONEY = "PetInfo_Money"
        const val TABLE_PINFO_EGGTYPE = "PetInfo_EggType"

        private const val CREATE_TABLE_PETINFO = "CREATE TABLE $TABLE_PINFO_TABLE ($TABLE_PINFO_NAME TEXT DEFAULT '', $TABLE_PINFO_AGE INTEGER, $TABLE_PINFO_LEVEL INTEGER, $TABLE_PINFO_GENDER TEXT DEFAULT '', $TABLE_PINFO_STAGE TEXT, $TABLE_PINFO_MONEY INTEGER, $TABLE_PINFO_EGGTYPE TEXT DEFAULT '')"

        const val TABLE_PSTATUS_TABLE = "PetStatus_Table"
        const val TABLE_PSTATUS_POINTS = "PetStatus_Points"
        const val TABLE_PSTATUS_STRENGTH = "PetStatus_Strength"
        const val TABLE_PSTATUS_SPEED = "PetStatus_Speed"
        const val TABLE_PSTATUS_HEALTH = "PetStatus_Health"
        const val TABLE_PSTATUS_EXP = "PetStatus_Exp"

        private const val CREATE_TABLE_PETSTATUS = "CREATE TABLE $TABLE_PSTATUS_TABLE ($TABLE_PSTATUS_POINTS INTEGER, $TABLE_PSTATUS_STRENGTH INTEGER, $TABLE_PSTATUS_SPEED INTEGER, $TABLE_PSTATUS_HEALTH INTEGER, $TABLE_PSTATUS_EXP INTEGER)"

        const val TABLE_PBAR_TABLE = "PetBar_Table"
        const val TABLE_PBAR_HUNGER = "PetBar_Hunger"
        const val TABLE_PBAR_HYGIENE = "PetBar_Hygiene"
        const val TABLE_PBAR_ENERGY = "PetBar_Energy"
        const val TABLE_PBAR_PLAY = "PetBar_Play"
        const val TABLE_PBAR_TRAIN = "PetBar_Train"

        private const val CREATE_TABLE_PETBAR = "CREATE TABLE $TABLE_PBAR_TABLE ($TABLE_PBAR_HUNGER INTEGER, $TABLE_PBAR_HYGIENE INTEGER, $TABLE_PBAR_ENERGY INTEGER, $TABLE_PBAR_PLAY INTEGER, $TABLE_PBAR_TRAIN INTEGER)"

        const val TABLE_PINVENTORY_TABLE = "PetInventory_Table"
        const val TABLE_PINVENTORY_FOOD_APPLE = "PetInventory_Food_Apple"
        const val TABLE_PINVENTORY_FOOD_BREAD = "PetInventory_Food_Bread"
        const val TABLE_PINVENTORY_FOOD_CAKE = "PetInventory_Food_Cake"
        const val TABLE_PINVENTORY_TOY_BAT = "PetInventory_Toy_Bat"
        const val TABLE_PINVENTORY_TOY_DRUMS = "PetInventory_Toy_Drums"
        const val TABLE_PINVENTORY_TOY_TV = "PetInventory_Toy_TV"
        const val TABLE_PINVENTORY_TRAIN_BOOMERANG = "PetInventory_Train_Boomerang"
        const val TABLE_PINVENTORY_TRAIN_BOW = "PetInventory_Train_Bow"
        const val TABLE_PINVENTORY_TRAIN_SWORD = "PetInventory_Train_Sword"

        private const val CREATE_TABLE_PETINVENTORY = "CREATE TABLE $TABLE_PINVENTORY_TABLE ($TABLE_PINVENTORY_FOOD_APPLE INTEGER, $TABLE_PINVENTORY_FOOD_BREAD INTEGER, $TABLE_PINVENTORY_FOOD_CAKE INTEGER, $TABLE_PINVENTORY_TOY_BAT INTEGER, $TABLE_PINVENTORY_TOY_DRUMS INTEGER, $TABLE_PINVENTORY_TOY_TV INTEGER, $TABLE_PINVENTORY_TRAIN_BOOMERANG INTEGER, $TABLE_PINVENTORY_TRAIN_BOW INTEGER, $TABLE_PINVENTORY_TRAIN_SWORD INTEGER)"


        const val TABLE_TIME_TABLE = "Time_Table"
        const val TABLE_TIME_PRESENT = "Time_Present"
        const val TABLE_DATE_CREATED = "Date_Created"

        private const val CREATE_TABLE_TIME = "CREATE TABLE $TABLE_TIME_TABLE ($TABLE_TIME_PRESENT TEXT, $TABLE_DATE_CREATED TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_PETINFO)
        db?.execSQL(CREATE_TABLE_PETSTATUS)
        db?.execSQL(CREATE_TABLE_PETBAR)
        db?.execSQL(CREATE_TABLE_PETINVENTORY)
        db?.execSQL(CREATE_TABLE_TIME)


        db?.execSQL("Insert into $TABLE_PINFO_TABLE ($TABLE_PINFO_LEVEL,$TABLE_PINFO_MONEY,$TABLE_PINFO_AGE,$TABLE_PINFO_STAGE) values (1, 0, 1, 'baby')")

        db?.execSQL("Insert into $TABLE_PSTATUS_TABLE ($TABLE_PSTATUS_EXP,$TABLE_PSTATUS_HEALTH,$TABLE_PSTATUS_POINTS,$TABLE_PSTATUS_SPEED,$TABLE_PSTATUS_STRENGTH) values (0, 0, 3, 0, 0)")

        db?.execSQL("Insert into $TABLE_PBAR_TABLE ($TABLE_PBAR_HUNGER,$TABLE_PBAR_HYGIENE,$TABLE_PBAR_ENERGY,$TABLE_PBAR_PLAY,$TABLE_PBAR_TRAIN) values (0, 100, 100, 0, 0)")

        db?.execSQL("Insert into $TABLE_TIME_TABLE ($TABLE_TIME_PRESENT,$TABLE_DATE_CREATED) values ('','')")

        db?.execSQL("Insert into $TABLE_PINVENTORY_TABLE ($TABLE_PINVENTORY_FOOD_APPLE,$TABLE_PINVENTORY_FOOD_BREAD,$TABLE_PINVENTORY_FOOD_CAKE," +
                "$TABLE_PINVENTORY_TOY_BAT,$TABLE_PINVENTORY_TOY_DRUMS,$TABLE_PINVENTORY_TOY_TV," +
                "$TABLE_PINVENTORY_TRAIN_BOOMERANG,$TABLE_PINVENTORY_TRAIN_BOW,$TABLE_PINVENTORY_TRAIN_SWORD) values (0, 0, 0, 0, 0, 0, 0, 0, 0)")



    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_PINFO_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PSTATUS_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PBAR_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PINVENTORY_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TIME_TABLE")


        onCreate(db)
    }





    fun addPetName (name: String){
        val db = this.writableDatabase

        val addPetName = ContentValues()

        addPetName.put(TABLE_PINFO_NAME, name)

        db.update(TABLE_PINFO_TABLE,addPetName, null,null)
        db.close()
    }


    fun addEggType (eggType: String){
        val db = this.writableDatabase

        val addEggType = ContentValues()

        addEggType.put(TABLE_PINFO_EGGTYPE, eggType)

        db.update(TABLE_PINFO_TABLE,addEggType, null,null)
        db.close()
    }


    fun addGender (gender: String){
        val db = this.writableDatabase

        val addGender = ContentValues()

        addGender.put(TABLE_PINFO_GENDER, gender)

        db.update(TABLE_PINFO_TABLE,addGender, null,null)
        db.close()
    }



    fun updateTime (time: String){
        val db = this.writableDatabase

        val updateTimeDB = ContentValues()

        updateTimeDB.put(TABLE_TIME_PRESENT, time)

        db.update(TABLE_TIME_TABLE,updateTimeDB, null,null)
        db.close()
    }


    fun getTime():String
    {
        var timeDB = ""
        val selectQuery = "SELECT $TABLE_TIME_PRESENT FROM $TABLE_TIME_TABLE"


        val db = this.readableDatabase
        var cursor:Cursor?=null

        try{
            cursor=db.rawQuery(selectQuery,null)
        }catch(e: SQLiteException)
        {
            db.close()
            return timeDB
        }



        cursor.moveToFirst()

        timeDB = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_TIME_PRESENT))



        return timeDB
    }


    fun updateDateCreated (date: String){
        val db = this.writableDatabase

        val updateDateDB = ContentValues()

        updateDateDB.put(TABLE_DATE_CREATED, date)

        db.update(TABLE_TIME_TABLE,updateDateDB, null,null)
        db.close()
    }

    fun getDateCreated():String
    {
        var dateDB = ""
        val selectQuery = "SELECT $TABLE_DATE_CREATED FROM $TABLE_TIME_TABLE"


        val db = this.readableDatabase
        var cursor:Cursor?=null

        try{
            cursor=db.rawQuery(selectQuery,null)
        }catch(e: SQLiteException)
        {
            db.close()
            return dateDB
        }



        cursor.moveToFirst()

        dateDB = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_DATE_CREATED))


        return dateDB
    }

    fun getEggType():String
    {
        var eggTypeDB = ""
        val selectQuery = "SELECT $TABLE_PINFO_EGGTYPE FROM $TABLE_PINFO_TABLE"


        val db = this.readableDatabase
        var cursor:Cursor?=null

        try{
            cursor=db.rawQuery(selectQuery,null)
        }catch(e: SQLiteException)
        {
            db.close()
            return eggTypeDB
        }



        cursor.moveToFirst()

        eggTypeDB = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_PINFO_EGGTYPE))



        return eggTypeDB
    }

    fun getPetInfo():PetInfo
    {

        var petInfoDB = PetInfo()
        val selectQuery = "SELECT * FROM $TABLE_PINFO_TABLE"


        val db = this.readableDatabase
        var cursor:Cursor?=null

        try{
            cursor=db.rawQuery(selectQuery,null)
        }catch(e: SQLiteException)
        {
            db.close()
            return PetInfo()
        }


        cursor.moveToFirst()

        petInfoDB.name = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_PINFO_NAME))
        petInfoDB.level = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PINFO_LEVEL))
        petInfoDB.gender = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_PINFO_GENDER))
        petInfoDB.age = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PINFO_AGE))
        petInfoDB.stage = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_PINFO_STAGE))
        petInfoDB.eggType = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_PINFO_EGGTYPE))
        petInfoDB.money = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PINFO_MONEY))




        return petInfoDB
    }


    fun getPetStatus():PetStatus
    {

        var petStatusDB = PetStatus()
        val selectQuery = "SELECT * FROM $TABLE_PSTATUS_TABLE"


        val db = this.readableDatabase
        var cursor:Cursor?=null

        try{
            cursor=db.rawQuery(selectQuery,null)
        }catch(e: SQLiteException)
        {
            db.close()
            return PetStatus()
        }


        cursor.moveToFirst()

        petStatusDB.exp = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PSTATUS_EXP))
        petStatusDB.points = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PSTATUS_POINTS))
        petStatusDB.health = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PSTATUS_HEALTH))
        petStatusDB.speed = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PSTATUS_SPEED))
        petStatusDB.strength = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PSTATUS_STRENGTH))





        return petStatusDB
    }


    fun getPetInventory():PetInventory
    {

        var petInventoryDB = PetInventory()
        val selectQuery = "SELECT * FROM $TABLE_PINVENTORY_TABLE"


        val db = this.readableDatabase
        var cursor:Cursor?=null

        try{
            cursor=db.rawQuery(selectQuery,null)
        }catch(e: SQLiteException)
        {
            db.close()
            return PetInventory()
        }


        cursor.moveToFirst()

        petInventoryDB.apple = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PINVENTORY_FOOD_APPLE))
        petInventoryDB.bread = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PINVENTORY_FOOD_BREAD))
        petInventoryDB.cake = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PINVENTORY_FOOD_CAKE))
        petInventoryDB.bat = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PINVENTORY_TOY_BAT))
        petInventoryDB.drums = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PINVENTORY_TOY_DRUMS))
        petInventoryDB.tv = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PINVENTORY_TOY_TV))
        petInventoryDB.boomerang = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PINVENTORY_TRAIN_BOOMERANG))
        petInventoryDB.bow = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PINVENTORY_TRAIN_BOW))
        petInventoryDB.sword = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PINVENTORY_TRAIN_SWORD))


        return petInventoryDB
    }

    fun updatePetInventory (petInventoryDB: PetInventory){
        val db = this.writableDatabase

        val updatePetInventoryDB = ContentValues()

        updatePetInventoryDB.put(TABLE_PINVENTORY_FOOD_APPLE, petInventoryDB.apple)
        updatePetInventoryDB.put(TABLE_PINVENTORY_FOOD_BREAD, petInventoryDB.bread)
        updatePetInventoryDB.put(TABLE_PINVENTORY_FOOD_CAKE, petInventoryDB.cake)
        updatePetInventoryDB.put(TABLE_PINVENTORY_TOY_BAT, petInventoryDB.bat)
        updatePetInventoryDB.put(TABLE_PINVENTORY_TOY_DRUMS, petInventoryDB.drums)
        updatePetInventoryDB.put(TABLE_PINVENTORY_TOY_TV, petInventoryDB.tv)
        updatePetInventoryDB.put(TABLE_PINVENTORY_TRAIN_BOOMERANG, petInventoryDB.boomerang)
        updatePetInventoryDB.put(TABLE_PINVENTORY_TRAIN_BOW, petInventoryDB.bow)
        updatePetInventoryDB.put(TABLE_PINVENTORY_TRAIN_SWORD, petInventoryDB.sword)

        db.update(TABLE_PINVENTORY_TABLE,updatePetInventoryDB, null,null)
        db.close()
    }



    fun getPetBar():PetBar
    {

        var petBarDB = PetBar()
        val selectQuery = "SELECT * FROM $TABLE_PBAR_TABLE"


        val db = this.readableDatabase
        var cursor:Cursor?=null

        try{
            cursor=db.rawQuery(selectQuery,null)
        }catch(e: SQLiteException)
        {
            db.close()
            return PetBar()
        }


        cursor.moveToFirst()

        petBarDB.hunger = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PBAR_HUNGER))
        petBarDB.hygiene = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PBAR_HYGIENE))
        petBarDB.energy = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PBAR_ENERGY))
        petBarDB.play = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PBAR_PLAY))
        petBarDB.train = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_PBAR_TRAIN))





        return petBarDB
    }

    fun updatePetBar (petBarDB: PetBar){
        val db = this.writableDatabase

        val updatePetBarDB = ContentValues()

        updatePetBarDB.put(TABLE_PBAR_HUNGER, petBarDB.hunger)
        updatePetBarDB.put(TABLE_PBAR_HYGIENE, petBarDB.hygiene)
        updatePetBarDB.put(TABLE_PBAR_ENERGY, petBarDB.energy)
        updatePetBarDB.put(TABLE_PBAR_PLAY, petBarDB.play)
        updatePetBarDB.put(TABLE_PBAR_TRAIN, petBarDB.train)

        db.update(TABLE_PBAR_TABLE,updatePetBarDB, null,null)
        db.close()
    }

    fun updatePetInfo (petInfoDB: PetInfo){
        val db = this.writableDatabase

        val updatePetInfoDB = ContentValues()

        updatePetInfoDB.put(TABLE_PINFO_LEVEL, petInfoDB.level)
        updatePetInfoDB.put(TABLE_PINFO_STAGE, petInfoDB.stage)
        updatePetInfoDB.put(TABLE_PINFO_AGE, petInfoDB.age)
        updatePetInfoDB.put(TABLE_PINFO_MONEY, petInfoDB.money)


        db.update(TABLE_PINFO_TABLE,updatePetInfoDB, null,null)
        db.close()
    }

    fun updatePetStatus (petStatusDB: PetStatus){
        val db = this.writableDatabase

        val updatePetStatusDB = ContentValues()

        updatePetStatusDB.put(TABLE_PSTATUS_EXP, petStatusDB.exp)
        updatePetStatusDB.put(TABLE_PSTATUS_HEALTH, petStatusDB.health)
        updatePetStatusDB.put(TABLE_PSTATUS_SPEED, petStatusDB.speed)
        updatePetStatusDB.put(TABLE_PSTATUS_STRENGTH, petStatusDB.strength)
        updatePetStatusDB.put(TABLE_PSTATUS_POINTS, petStatusDB.points)

        db.update(TABLE_PSTATUS_TABLE,updatePetStatusDB, null,null)
        db.close()
    }
}
