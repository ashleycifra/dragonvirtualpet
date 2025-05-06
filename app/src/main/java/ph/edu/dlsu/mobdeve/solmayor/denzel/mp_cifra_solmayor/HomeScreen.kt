package ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor



import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.DragEvent
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.DRAG_FLAG_OPAQUE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor.databinding.ActivityHomeScreenBinding

import java.text.SimpleDateFormat
import java.util.*


class HomeScreen : AppCompatActivity(), GestureDetector.OnGestureListener,
                                        View.OnTouchListener,View.OnDragListener,
                                        GestureDetector.OnDoubleTapListener{

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var mDetector: GestureDetector
    private val TAG:String = "HomeScreen"
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var animationDrawablePet: AnimationDrawable
    private lateinit var myPet:PetInfo
    private lateinit var time:String
    private lateinit var date: String
    private var databaseHandler = DatabaseHandler(this)
    private lateinit var timer: CountDownTimer
    private lateinit var sleeptimer: CountDownTimer
    private var ctr: Int = 0


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDetector = GestureDetector(this,this)

        binding.activityheader.setOnTouchListener(this)

        time = databaseHandler.getTime()
        date = databaseHandler.getDateCreated()

        val bundle =intent.extras

        if (bundle != null) {
            ctr = bundle.getInt("ctr")
        }

        if(time == "" || date == "")
        {
            updateTime()
            databaseHandler.updateDateCreated(Calendar.getInstance().time.toString())
        }


//        Log.d("ctr", ctr.toString())
        startTimer(ctr)

        myPet = databaseHandler.getPetInfo()
        myPet.petStatus = databaseHandler.getPetStatus()
        myPet.petBar = databaseHandler.getPetBar()
        myPet.petInventory = databaseHandler.getPetInventory()

        binding.petname.text = myPet.name

        binding.hungerbar.progress = myPet.petBar.hunger
        binding.hygienebar.progress = myPet.petBar.hygiene
        binding.energybar.progress = myPet.petBar.energy
        binding.playbar.progress = myPet.petBar.play
        binding.trainbar.progress = myPet.petBar.train



        if(myPet.eggType == "yellow")
        {

            if(myPet.stage == "baby")
            {
                val dp = 80f
                val px = dp * resources.displayMetrics.density
                binding.peticon.layoutParams.height = px.toInt()
                binding.peticon.layoutParams.width = px.toInt()
                binding.peticon.requestLayout()


                binding.peticon.setImageResource(R.drawable.animation_young_brass_dragon)

                binding.peticon.tag = "yellow"
            }

            else if(myPet.stage == "adult")
            {
                val dp = 120f
                val px = dp * resources.displayMetrics.density
                binding.peticon.layoutParams.height = px.toInt()
                binding.peticon.layoutParams.width = px.toInt()
                binding.peticon.requestLayout()
                binding.peticon.setImageResource(R.drawable.animation_adult_brass_dragon)

                val dp1 = 50f
                val px1 = dp1 * resources.displayMetrics.density
                binding.petreacts.layoutParams.height = px1.toInt()
                binding.petreacts.layoutParams.width = px1.toInt()
                binding.petreacts.requestLayout()

                binding.peticon.tag = "yellow"
            }

            else if(myPet.stage == "great")
            {
                val dp = 170f
                val px = dp * resources.displayMetrics.density
                binding.peticon.layoutParams.height = px.toInt()
                binding.peticon.layoutParams.width = px.toInt()
                binding.peticon.requestLayout()
                binding.peticon.setImageResource(R.drawable.animation_great_golden_wyrm)


                val dp1 = 60f
                val px1 = dp1 * resources.displayMetrics.density
                binding.petreacts.layoutParams.height = px1.toInt()
                binding.petreacts.layoutParams.width = px1.toInt()
                binding.petreacts.requestLayout()

                binding.peticon.tag = "yellow"
            }




        }

        else if(myPet.eggType == "red")
        {
            if(myPet.stage == "baby")
            {
                val dp = 80f
                val px = dp * resources.displayMetrics.density
                binding.peticon.layoutParams.height = px.toInt()
                binding.peticon.layoutParams.width = px.toInt()
                binding.peticon.requestLayout()
                binding.peticon.setImageResource(R.drawable.animation_young_red_dragon)

                binding.peticon.tag = "red"
            }

            else if(myPet.stage == "adult")
            {
                val dp = 120f
                val px = dp * resources.displayMetrics.density
                binding.peticon.layoutParams.height = px.toInt()
                binding.peticon.layoutParams.width = px.toInt()
                binding.peticon.requestLayout()
                binding.peticon.setImageResource(R.drawable.animation_adult_red_dragon)

                val dp1 = 50f
                val px1 = dp1 * resources.displayMetrics.density
                binding.petreacts.layoutParams.height = px1.toInt()
                binding.petreacts.layoutParams.width = px1.toInt()
                binding.petreacts.requestLayout()

                binding.peticon.tag = "red"
            }

            else if(myPet.stage == "great")
            {
                val dp = 170f
                val px = dp * resources.displayMetrics.density
                binding.peticon.layoutParams.height = px.toInt()
                binding.peticon.layoutParams.width = px.toInt()
                binding.peticon.requestLayout()
                binding.peticon.setImageResource(R.drawable.animation_great_red_wyrm)



                val dp1 = 60f
                val px1 = dp1 * resources.displayMetrics.density
                binding.petreacts.layoutParams.height = px1.toInt()
                binding.petreacts.layoutParams.width = px1.toInt()
                binding.petreacts.requestLayout()
                binding.peticon.tag = "red"
            }
        }


        animationDrawablePet = binding.peticon.drawable as AnimationDrawable
        animationDrawable = AnimationDrawable()

        binding.profilenav.setOnClickListener {
            val goToProfile = Intent(this,ProfileScreen::class.java)
            val bundle = Bundle()

            if(::sleeptimer.isInitialized)
            {
                sleeptimer.cancel()
            }

            timer.cancel()
            bundle.putInt("ctr",ctr)


            goToProfile.putExtras(bundle)


            startActivity(goToProfile)
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            finish()

        }

        binding.shopnav.setOnClickListener {
            val goToStore = Intent(this,StoreScreen::class.java)

            val bundle = Bundle()

            if(::sleeptimer.isInitialized)
            {
                sleeptimer.cancel()
            }

            timer.cancel()
            bundle.putInt("ctr",ctr)


            goToStore.putExtras(bundle)
            startActivity(goToStore)
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            finish()

        }

        binding.arenanav.setOnClickListener {
            val goToArenaScreen = Intent(this,ArenaScreen::class.java)


            val bundle = Bundle()

            if(::sleeptimer.isInitialized)
            {
                sleeptimer.cancel()
            }
            timer.cancel()
            bundle.putInt("ctr",ctr)



            goToArenaScreen.putExtras(bundle)
            startActivity(goToArenaScreen)
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            finish()

        }


        binding.inventoryicon.setOnClickListener {
            if(binding.inventoryicon.tag == "unclicked")
            {
                binding.inventoryicon.setImageResource(R.drawable.inventory_clicked)
                binding.inventoryicon.tag = "clicked"


                if(binding.foodicon.tag == "clicked") {
                    binding.inventoryitem1.setImageResource(R.drawable.food_chicken)
                    binding.inventoryitem1.tag = "food"
                    binding.inventoryitem1.setOnTouchListener(this)

                    if(myPet.petInventory.apple != 0) {
                        binding.inventoryitem2.setImageResource(R.drawable.foodstore_apple)
                        binding.inventoryitem2.tag = "food1"
                        binding.inventoryitem2.setOnTouchListener(this)
                    }

                    if(myPet.petInventory.bread != 0) {

                        binding.inventoryitem3.setImageResource(R.drawable.foodstore_bread)
                        binding.inventoryitem3.tag = "food2"
                        binding.inventoryitem3.setOnTouchListener(this)
                    }

                    if(myPet.petInventory.cake != 0) {
                        binding.inventoryitem4.setImageResource(R.drawable.foodstore_cake)
                        binding.inventoryitem4.tag = "food3"
                        binding.inventoryitem4.setOnTouchListener(this)
                    }

                }

                else if(binding.playicon.tag == "clicked")
                {
                    binding.inventoryitem1.setImageResource(R.drawable.play_ball)
                    binding.inventoryitem1.tag = "play"
                    binding.inventoryitem1.setOnTouchListener(this)

                    if(myPet.petInventory.bat != 0) {
                        binding.inventoryitem2.setImageResource(R.drawable.toystore_bat)
                        binding.inventoryitem2.tag = "play1"
                        binding.inventoryitem2.setOnTouchListener(this)
                    }

                    if(myPet.petInventory.drums != 0) {
                        binding.inventoryitem3.setImageResource(R.drawable.toystore_drums)
                        binding.inventoryitem3.tag = "play2"
                        binding.inventoryitem3.setOnTouchListener(this)
                    }

                    if(myPet.petInventory.tv != 0) {
                        binding.inventoryitem4.setImageResource(R.drawable.toystore_tv)
                        binding.inventoryitem4.tag = "play3"
                        binding.inventoryitem4.setOnTouchListener(this)
                    }

                }

                else if(binding.trainicon.tag == "clicked")
                {
                    binding.inventoryitem1.setImageResource(R.drawable.train_knife)
                    binding.inventoryitem1.tag = "train"
                    binding.inventoryitem1.setOnTouchListener(this)

                    if(myPet.petInventory.boomerang != 0) {
                        binding.inventoryitem2.setImageResource(R.drawable.trainstore_boomerang)
                        binding.inventoryitem2.tag = "train1"
                        binding.inventoryitem2.setOnTouchListener(this)
                    }
                    if(myPet.petInventory.bow != 0) {
                        binding.inventoryitem3.setImageResource(R.drawable.trainstore_bow)
                        binding.inventoryitem3.tag = "train2"
                        binding.inventoryitem3.setOnTouchListener(this)
                    }


                    if(myPet.petInventory.sword != 0) {
                        binding.inventoryitem4.setImageResource(R.drawable.trainstore_sword)
                        binding.inventoryitem4.tag = "train3"
                        binding.inventoryitem4.setOnTouchListener(this)
                    }


                }
            }

           else if(binding.inventoryicon.tag == "clicked")
            {
                binding.inventoryicon.setImageResource(R.drawable.inventory_unclicked)
                binding.inventoryicon.tag = "unclicked"

                binding.inventoryitem1.setImageResource(0)
                binding.inventoryitem2.setImageResource(0)
                binding.inventoryitem3.setImageResource(0)
                binding.inventoryitem4.setImageResource(0)

            }

        }







        binding.showericon.setOnClickListener {

            if (binding.showericon.tag.toString() == "unclicked") {

                Toast.makeText(this,"Swipe cloud to shower",Toast.LENGTH_SHORT).show()

                binding.petreacts.setImageResource(0)
                animationDrawable.stop()
                if(::sleeptimer.isInitialized)
                {
                    sleeptimer.cancel()
                }
                binding.inventoryicon.setImageResource(R.drawable.inventory_unclicked)
                binding.inventoryitem1.setImageResource(0)
                binding.inventoryitem2.setImageResource(0)
                binding.inventoryitem3.setImageResource(0)
                binding.inventoryitem4.setImageResource(0)

                animationDrawablePet.stop()
                binding.inventoryicon.visibility = View.INVISIBLE
                binding.cardview.setCardBackgroundColor(ContextCompat.getColor(applicationContext,R.color.whitetransparent))

                binding.activityheader.setImageResource(R.drawable.headershower)
                binding.activityheader.tag="showerheader"


                binding.showericon.setImageResource(R.drawable.shower_clicked)
                binding.showericon.tag = "clicked"

                binding.foodicon.setImageResource(R.drawable.food_unclicked)
                binding.foodicon.tag = "unclicked"

                binding.playicon.setImageResource(R.drawable.play_unclicked)
                binding.playicon.tag = "unclicked"

                binding.sleepicon.setImageResource(R.drawable.sleep_unclicked)
                binding.sleepicon.tag = "unclicked"

                binding.trainicon.setImageResource(R.drawable.train_unclicked)
                binding.trainicon.tag = "unclicked"

            }

            else if(binding.showericon.tag.toString() == "clicked")
            {
                binding.activityheader.setImageResource(0)


                binding.showericon.setImageResource(R.drawable.shower_unclicked)
                binding.showericon.tag = "unclicked"
            }
        }


        binding.sleepicon.setOnClickListener {

            sleeptimer = object: CountDownTimer(8000, 1000){
                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {

                    myPet.petBar.energy += 10
                    if(myPet.petBar.energy >= 100)
                    {
                        myPet.petBar.energy = 100
                    }
                    binding.energybar.progress = myPet.petBar.energy
                    databaseHandler.updatePetBar(myPet.petBar)

                        if(binding.sleepicon.tag.toString() == "clicked")
                        {
                            start()
                        }
                            else
                            {
                                cancel()
                            }

                }

            }

            if (binding.sleepicon.tag.toString() == "unclicked") {
                binding.activityheader.setImageResource(R.drawable.moon)
                animationDrawablePet.stop()
                binding.petreacts.setImageResource(0)

                binding.inventoryicon.visibility = View.INVISIBLE

                binding.inventoryicon.setImageResource(R.drawable.inventory_unclicked)
                binding.inventoryitem1.setImageResource(0)
                binding.inventoryitem2.setImageResource(0)
                binding.inventoryitem3.setImageResource(0)
                binding.inventoryitem4.setImageResource(0)


                binding.petreacts.setImageResource(R.drawable.animation_sleep)
                animationDrawable =binding.petreacts.drawable as AnimationDrawable
                animationDrawable.start()


                    binding.cardview.setCardBackgroundColor(ContextCompat.getColor(applicationContext,R.color.blacktransparent))
                binding.activityheader.tag="sleepheader"

                binding.sleepicon.setImageResource(R.drawable.sleep_clicked)
                binding.sleepicon.tag = "clicked"

                binding.foodicon.setImageResource(R.drawable.food_unclicked)
                binding.foodicon.tag = "unclicked"

                binding.playicon.setImageResource(R.drawable.play_unclicked)
                binding.playicon.tag = "unclicked"

                binding.showericon.setImageResource(R.drawable.shower_unclicked)
                binding.showericon.tag = "unclicked"

                binding.trainicon.setImageResource(R.drawable.train_unclicked)
                binding.trainicon.tag = "unclicked"

                sleeptimer.start()

            }

            else if(binding.sleepicon.tag.toString() == "clicked")
            {
                if(::sleeptimer.isInitialized)
                {
                    sleeptimer.cancel()
                }
                binding.activityheader.setImageResource(0)
                binding.petreacts.setImageResource(0)
                animationDrawable.stop()

                binding.cardview.setCardBackgroundColor(ContextCompat.getColor(applicationContext,R.color.whitetransparent))
                binding.sleepicon.setImageResource(R.drawable.sleep_unclicked)
                binding.sleepicon.tag = "unclicked"
            }
        }





        binding.foodicon.setOnClickListener {

            if (binding.foodicon.tag.toString() == "unclicked") {

                Toast.makeText(this,"Drag food from inventory to feed",Toast.LENGTH_SHORT).show()
                binding.petreacts.setImageResource(0)
                animationDrawable.stop()
                animationDrawablePet.stop()
                if(::sleeptimer.isInitialized)
                {
                    sleeptimer.cancel()
                }


                binding.inventoryicon.visibility = View.VISIBLE
                binding.inventoryicon.setImageResource(R.drawable.inventory_unclicked)
                binding.inventoryitem1.setImageResource(0)
                binding.inventoryitem2.setImageResource(0)
                binding.inventoryitem3.setImageResource(0)
                binding.inventoryitem4.setImageResource(0)
                binding.petreacts.setImageResource(0)


                binding.cardview.setCardBackgroundColor(ContextCompat.getColor(applicationContext,R.color.whitetransparent))
                binding.activityheader.setImageResource(R.drawable.headerfood)
                binding.activityheader.tag="foodheader"

                binding.foodicon.setImageResource(R.drawable.food_clicked)
                binding.foodicon.tag = "clicked"

                binding.playicon.setImageResource(R.drawable.play_unclicked)
                binding.playicon.tag = "unclicked"

                binding.sleepicon.setImageResource(R.drawable.sleep_unclicked)
                binding.sleepicon.tag = "unclicked"

                binding.showericon.setImageResource(R.drawable.shower_unclicked)
                binding.showericon.tag = "unclicked"

                binding.trainicon.setImageResource(R.drawable.train_unclicked)
                binding.trainicon.tag = "unclicked"
            }

            else if(binding.foodicon.tag.toString() == "clicked")
            {
                binding.activityheader.setImageResource(0)
                binding.inventoryicon.visibility = View.INVISIBLE
                binding.inventoryicon.setImageResource(R.drawable.inventory_unclicked)
                binding.inventoryitem1.setImageResource(0)
                binding.inventoryitem2.setImageResource(0)
                binding.inventoryitem3.setImageResource(0)
                binding.inventoryitem4.setImageResource(0)

                binding.foodicon.setImageResource(R.drawable.food_unclicked)
                binding.foodicon.tag = "unclicked"
            }
        }


        binding.playicon.setOnClickListener {

            if (binding.playicon.tag.toString() == "unclicked") {

                Toast.makeText(this,"Drag toy from inventory to play",Toast.LENGTH_SHORT).show()
                animationDrawablePet.stop()
                animationDrawable.stop()
                if(::sleeptimer.isInitialized)
                {
                    sleeptimer.cancel()
                }
                binding.inventoryicon.visibility = View.VISIBLE
                binding.inventoryicon.setImageResource(R.drawable.inventory_unclicked)
                binding.inventoryitem1.setImageResource(0)
                binding.inventoryitem2.setImageResource(0)
                binding.inventoryitem3.setImageResource(0)
                binding.inventoryitem4.setImageResource(0)
                binding.petreacts.setImageResource(0)

                binding.activityheader.setImageResource(R.drawable.headerplay)
                binding.activityheader.tag="playheader"
                binding.cardview.setCardBackgroundColor(ContextCompat.getColor(applicationContext,R.color.whitetransparent))

                binding.playicon.setImageResource(R.drawable.play_clicked)
                binding.playicon.tag = "clicked"


                binding.foodicon.setImageResource(R.drawable.food_unclicked)
                binding.foodicon.tag = "unclicked"

                binding.sleepicon.setImageResource(R.drawable.sleep_unclicked)
                binding.sleepicon.tag = "unclicked"

                binding.showericon.setImageResource(R.drawable.shower_unclicked)
                binding.showericon.tag = "unclicked"

                binding.trainicon.setImageResource(R.drawable.train_unclicked)
                binding.trainicon.tag = "unclicked"


            }

            else if(binding.playicon.tag.toString() == "clicked")
            {
                binding.inventoryicon.visibility = View.INVISIBLE
                binding.activityheader.setImageResource(0)
                binding.inventoryicon.setImageResource(R.drawable.inventory_unclicked)
                binding.inventoryitem1.setImageResource(0)
                binding.inventoryitem2.setImageResource(0)
                binding.inventoryitem3.setImageResource(0)
                binding.inventoryitem4.setImageResource(0)

                binding.playicon.setImageResource(R.drawable.play_unclicked)
                binding.playicon.tag = "unclicked"
            }
        }

        binding.trainicon.setOnClickListener {

            if (binding.trainicon.tag.toString() == "unclicked") {

                Toast.makeText(this,"Drag weapons from inventory to train",Toast.LENGTH_SHORT).show()
                animationDrawablePet.stop()
                animationDrawable.stop()
                if(::sleeptimer.isInitialized)
                {
                    sleeptimer.cancel()
                }
                binding.inventoryicon.visibility = View.VISIBLE
                binding.inventoryicon.setImageResource(R.drawable.inventory_unclicked)
                binding.inventoryitem1.setImageResource(0)
                binding.inventoryitem2.setImageResource(0)
                binding.inventoryitem3.setImageResource(0)
                binding.inventoryitem4.setImageResource(0)
                binding.petreacts.setImageResource(0)




                binding.activityheader.setImageResource(R.drawable.headertrain)
                binding.activityheader.tag="trainheader"

                binding.cardview.setCardBackgroundColor(ContextCompat.getColor(applicationContext,R.color.whitetransparent))
                binding.trainicon.setImageResource(R.drawable.train_clicked)
                binding.trainicon.tag = "clicked"


                binding.foodicon.setImageResource(R.drawable.food_unclicked)
                binding.foodicon.tag = "unclicked"

                binding.playicon.setImageResource(R.drawable.play_unclicked)
                binding.playicon.tag = "unclicked"

                binding.sleepicon.setImageResource(R.drawable.sleep_unclicked)
                binding.sleepicon.tag = "unclicked"

                binding.showericon.setImageResource(R.drawable.shower_unclicked)
                binding.showericon.tag = "unclicked"

            }

            else if(binding.trainicon.tag.toString() == "clicked")
            {
                binding.activityheader.setImageResource(0)
                binding.inventoryicon.visibility = View.INVISIBLE
                binding.inventoryicon.setImageResource(R.drawable.inventory_unclicked)
                binding.inventoryitem1.setImageResource(0)
                binding.inventoryitem2.setImageResource(0)
                binding.inventoryitem3.setImageResource(0)
                binding.inventoryitem4.setImageResource(0)

                binding.trainicon.setImageResource(R.drawable.train_unclicked)
                binding.trainicon.tag = "unclicked"
            }
        }






    }

    fun updateTime()
    {
        var currentTime = Calendar.getInstance().time.toString()
        databaseHandler.updateTime(currentTime)
        time = currentTime
    }

    fun startTimer(extraTime:Int)
    {

        var threeMinutes : Long = 180000

        threeMinutes -= extraTime
        //3mins = 180000


      timer = object: CountDownTimer(threeMinutes, 1000){
            override fun onTick(millisUntilFinished: Long) {
//                Log.d("OnTick","Timer tick $ctr millis")
                ctr+=1000
            }

            override fun onFinish() {
//                Log.d("OnFinish","Timer finished")
                ctr = 0
                myPet.petBar.hygiene -= 10
                myPet.petBar.hunger -= 10
                myPet.petBar.train -= 10
                myPet.petBar.hygiene -= 10

                if(myPet.petBar.hygiene < 0)
                {
                    myPet.petBar.hygiene = 0
                }

                if(myPet.petBar.hunger < 0)
                {
                    myPet.petBar.hunger = 0
                }

                if(myPet.petBar.train < 0)
                {
                    myPet.petBar.train = 0
                }

                if(myPet.petBar.play < 0)
                {
                    myPet.petBar.play = 0
                }

                binding.hungerbar.progress = myPet.petBar.hunger
                binding.hygienebar.progress = myPet.petBar.hygiene
                binding.playbar.progress = myPet.petBar.play
                binding.trainbar.progress = myPet.petBar.train

                databaseHandler.updatePetBar(myPet.petBar)
                startTimer(0)
            }

        }.start()




    }

    override fun onDestroy() {

        updateTime()
        super.onDestroy()
    }



    /*
        Gestures (Long Press and Fling)
     */
    override fun onDown(e: MotionEvent): Boolean {
        Log.d(TAG,"onDown: called.")
        return false
    }

    override fun onShowPress(e: MotionEvent) {
        Log.d(TAG,"onShowPress: called.")

    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        Log.d(TAG,"onSingleTapUp: called.")
        return false
    }

    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        Log.d(TAG,"onScroll: called.")
        return false
    }

    override fun onLongPress(e: MotionEvent) {

        var builder: View.DragShadowBuilder = View.DragShadowBuilder(binding.inventoryitem1)
        var builder1: View.DragShadowBuilder = View.DragShadowBuilder(binding.inventoryitem2)
        var builder2: View.DragShadowBuilder = View.DragShadowBuilder(binding.inventoryitem3)
        var builder3: View.DragShadowBuilder = View.DragShadowBuilder(binding.inventoryitem4)

        when (e.source) {

            //2-5 food
            2 -> {
                Log.d(TAG,"onLongPress: called. ${e.source}")

                binding.inventoryitem1.startDragAndDrop(null,builder,"food",DRAG_FLAG_OPAQUE)
            }
            3 -> {
                Log.d(TAG,"onLongPress: called. ${e.source}")

                binding.inventoryitem2.startDragAndDrop(null,builder1,"food1",DRAG_FLAG_OPAQUE)
            }
            4 -> {
                Log.d(TAG,"onLongPress: called. ${e.source}")

                binding.inventoryitem3.startDragAndDrop(null,builder2,"food2",DRAG_FLAG_OPAQUE)
            }
            5 -> {
                Log.d(TAG,"onLongPress: called. ${e.source}")
                binding.inventoryitem4.startDragAndDrop(null,builder3,"food3",DRAG_FLAG_OPAQUE)
            }

            //6-9 play
            6 -> {

                Log.d(TAG,"onLongPress: called. ${e.source}")

                binding.inventoryitem1.startDragAndDrop(null,builder,"play",DRAG_FLAG_OPAQUE)

            }
            7 -> {

                Log.d(TAG,"onLongPress: called. ${e.source}")

                binding.inventoryitem2.startDragAndDrop(null,builder1,"play1",DRAG_FLAG_OPAQUE)

            }
            8 -> {

                Log.d(TAG,"onLongPress: called. ${e.source}")

                binding.inventoryitem3.startDragAndDrop(null,builder2,"play2",DRAG_FLAG_OPAQUE)

            }
            9 -> {

                Log.d(TAG,"onLongPress: called. ${e.source}")

                binding.inventoryitem4.startDragAndDrop(null,builder3,"play3",DRAG_FLAG_OPAQUE)

            //10-13 train
            }
            10 -> {
                Log.d(TAG,"onLongPress: called. ${e.source}")
                binding.inventoryitem1.startDragAndDrop(null,builder,"train",DRAG_FLAG_OPAQUE)

            }
            11 -> {
                Log.d(TAG,"onLongPress: called. ${e.source}")
                binding.inventoryitem2.startDragAndDrop(null,builder1,"train1",DRAG_FLAG_OPAQUE)

            }
            12 -> {
                Log.d(TAG,"onLongPress: called. ${e.source}")
                binding.inventoryitem3.startDragAndDrop(null,builder2,"train2",DRAG_FLAG_OPAQUE)

            }
            13 -> {
                Log.d(TAG,"onLongPress: called. ${e.source}")
                binding.inventoryitem4.startDragAndDrop(null,builder3,"train3",DRAG_FLAG_OPAQUE)

            }

        }

        binding.peticon.setOnDragListener(this)

    }

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.d(TAG,"onFling: called. x: $velocityX y: $velocityY")

        if(e1.source == 1 || e2.source == 1) {

            binding.petreacts.setImageResource(R.drawable.animation_shower)
            animationDrawable = binding.petreacts.drawable as AnimationDrawable
            animationDrawable.start()
            animationDrawablePet.start()

            Handler(mainLooper).postDelayed(
                {
                    binding.petreacts.setImageResource(0)
                    animationDrawablePet.stop()
                },
                2000
            ) // 2 seconds

            if (myPet.petBar.hygiene != 100) {
                myPet.petBar.hygiene += 10

                if (myPet.petBar.hygiene >= 100)
                {
                    myPet.petBar.hygiene = 100
                }


                databaseHandler.updatePetBar(myPet.petBar)
            }

            binding.hygienebar.progress = myPet.petBar.hygiene
        }

        return false
    }


    /*
        Touch Event
        */
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        if (event != null) {
            if (v!!.tag == binding.activityheader.tag && binding.showericon.tag == "clicked") {

                //1 == Shower
                event.source = 1
                mDetector.onTouchEvent(event)
                return true


            }

                else if(binding.inventoryicon.tag == "clicked")
                {

                    // 2-5 == Food Inventory
                    when (v.tag) {
                        "food" -> {
                            event.source = 2
                            mDetector.onTouchEvent(event)
                            return true
                        }
                        "food1" -> {
                            event.source = 3
                            mDetector.onTouchEvent(event)
                            return true
                        }
                        "food2" -> {
                            event.source = 4
                            mDetector.onTouchEvent(event)
                            return true

                        }
                        "food3" -> {
                            event.source = 5
                            mDetector.onTouchEvent(event)
                            return true

                        }


                        // 6-9 == Play Inventory
                        "play" -> {
                            event.source = 6
                            mDetector.onTouchEvent(event)
                            return true
                        }
                        "play1" -> {
                            event.source = 7
                            mDetector.onTouchEvent(event)
                            return true
                        }
                        "play2" -> {
                            event.source = 8
                            mDetector.onTouchEvent(event)
                            return true
                        }
                        "play3" -> {
                            event.source = 9
                            mDetector.onTouchEvent(event)
                            return true
                        }


                        // 10-13 == Train Inventory
                        "train" -> {
                            event.source = 10
                            mDetector.onTouchEvent(event)
                            return true
                        }
                        "train1" -> {
                            event.source = 11
                            mDetector.onTouchEvent(event)
                            return true
                        }
                        "train2" -> {
                            event.source = 12
                            mDetector.onTouchEvent(event)
                            return true
                        }
                        "train3" -> {
                            event.source = 13
                            mDetector.onTouchEvent(event)
                            return true
                        }
                    }



                }


        }


        return true
    }

    /*
        Dragging

        localState differentiates dragged item
     */

    override fun onDrag(v: View?, dragEvent: DragEvent?): Boolean {
        when (dragEvent!!.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                Log.d(TAG, "onDrag: drag started. ${dragEvent.toString()}")

                return true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                Log.d(TAG, "onDrag: drag entered.")
                return true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                Log.d(
                    TAG,
                    "onDrag: current point: ( ${dragEvent.x} ${dragEvent.y} )"
                )
                return true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                Log.d(TAG, "onDrag: exited.")
                return true
            }
            DragEvent.ACTION_DROP -> {

                when (dragEvent.localState) {
                    "food" -> {

                        HungerUpdate(5,true,0)


                    }
                    "food1" -> {

                        HungerUpdate(8,false,1)


                    }
                    "food2" -> {

                        HungerUpdate(10,false,2)


                    }
                    "food3" -> {

                        HungerUpdate(12,false,3)


                    }
                    "play" -> {

                        PlayUpdate(10,true,0)


                    }
                    "play1" -> {

                        PlayUpdate(15,false,1)


                    }
                    "play2" -> {

                        PlayUpdate(18,false,2)


                    }
                    "play3" -> {

                        PlayUpdate(21,false,3)


                    }
                    "train" -> {
                        TrainUpdate(15,true,0)

                    }
                    "train1" -> {
                        TrainUpdate(20,false,1)

                    }
                    "train2" -> {
                        TrainUpdate(23,false,2)

                    }
                    "train3" -> {
                        TrainUpdate(27,false,3)

                    }
                }

                Log.d(TAG, "onDrag: dropped.")
                return true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                Log.d(TAG, "onDrag: ended.")



                return true
            }
            else -> Log.e(TAG, "Unknown action type received by OnStartDragListener.")
        }
        return false
    }


    fun HungerUpdate(addExp:Int,money:Boolean,itemType: Int)
    {
        if(myPet.petBar.hunger != 100  && myPet.petBar.energy >= 20)
        {
            binding.petreacts.setImageResource(R.drawable.animation_food)
            animationDrawable = binding.petreacts.drawable as AnimationDrawable
            animationDrawable.start()
            animationDrawablePet.start()

            Handler(mainLooper).postDelayed(
                { binding.petreacts.setImageResource(0)
                    animationDrawablePet.stop()                },
                2000
            ) // 2 seconds

            myPet.petBar.hunger += 5
            myPet.petBar.energy -= 2

            databaseHandler.updatePetBar(myPet.petBar)

            if(money)
            {
                myPet.money += 3

            }

            myPet.petStatus.exp += addExp

            if(myPet.petStatus.exp >= 200)
            {
                myPet.petStatus.exp = 0
                myPet.level += 1
                myPet.petStatus.points += 3
            }

            when (itemType) {
                1 -> {
                    myPet.petInventory.apple -= 1

                    if (myPet.petInventory.apple <=0) {
                        myPet.petInventory.apple = 0
                        binding.inventoryitem2.visibility = View.INVISIBLE
                    }
                }
                2 -> {
                    myPet.petInventory.bread -= 1

                    if (myPet.petInventory.bread <=0) {
                        myPet.petInventory.bread = 0
                        binding.inventoryitem3.visibility = View.INVISIBLE
                    }
                }
                3 -> {
                    myPet.petInventory.cake -= 1

                    if (myPet.petInventory.cake <=0) {
                        myPet.petInventory.cake = 0
                        binding.inventoryitem4.visibility = View.INVISIBLE
                    }
                }
            }

            databaseHandler.updatePetInfo(myPet)
            databaseHandler.updatePetStatus(myPet.petStatus)
            databaseHandler.updatePetInventory(myPet.petInventory)
            binding.hungerbar.progress = myPet.petBar.hunger
            binding.hygienebar.progress = myPet.petBar.hygiene
            binding.energybar.progress = myPet.petBar.energy
        }

        else if(myPet.petBar.energy < 20)
        {
            Toast.makeText(this,"Not Enough Energy!",Toast.LENGTH_SHORT).show()
        }
    }

    fun PlayUpdate(addExp: Int,money: Boolean, itemType: Int)
    {
        if(myPet.petBar.play != 100 && myPet.petBar.hygiene >= 25 && myPet.petBar.energy >= 25)
        {
            binding.petreacts.setImageResource(R.drawable.animation_play)
            animationDrawable = binding.petreacts.drawable as AnimationDrawable
            animationDrawable.start()
            animationDrawablePet.start()

            Handler(mainLooper).postDelayed(
                { binding.petreacts.setImageResource(0)
                    animationDrawablePet.stop()                },
                2000
            ) // 2 seconds



            myPet.petBar.play += 5
            myPet.petBar.energy -= 5
            myPet.petBar.hygiene -= 3
            databaseHandler.updatePetBar(myPet.petBar)

            if (money)
            {
                myPet.money += 5
            }


            myPet.petStatus.exp += addExp

            if(myPet.petStatus.exp >= 200)
            {
                myPet.petStatus.exp = 0
                myPet.level += 1
                myPet.petStatus.points += 3
            }


            when (itemType) {
                1 -> {
                    myPet.petInventory.bat -= 1

                    if (myPet.petInventory.bat <=0) {
                        myPet.petInventory.bat = 0
                        binding.inventoryitem2.visibility = View.INVISIBLE
                    }
                }
                2 -> {
                    myPet.petInventory.drums -= 1

                    if (myPet.petInventory.drums <=0) {
                        myPet.petInventory.drums = 0
                        binding.inventoryitem3.visibility = View.INVISIBLE
                    }
                }
                3 -> {
                    myPet.petInventory.tv -= 1

                    if (myPet.petInventory.tv <=0) {
                        myPet.petInventory.tv = 0
                        binding.inventoryitem4.visibility = View.INVISIBLE
                    }
                }
            }

            databaseHandler.updatePetInfo(myPet)
            databaseHandler.updatePetStatus(myPet.petStatus)
            databaseHandler.updatePetInventory(myPet.petInventory)
            binding.playbar.progress = myPet.petBar.play
            binding.hygienebar.progress = myPet.petBar.hygiene
            binding.energybar.progress = myPet.petBar.energy
        }

        else if(myPet.petBar.energy < 25)
        {
            Toast.makeText(this,"Not Enough Energy!",Toast.LENGTH_SHORT).show()
        }

        else if(myPet.petBar.hygiene < 25)
        {
            Toast.makeText(this,"Not Enough Hygiene!",Toast.LENGTH_SHORT).show()
        }
    }

    fun TrainUpdate(addExp: Int,money: Boolean, itemType: Int)
    {
        if(myPet.petBar.train != 100 && myPet.petBar.hygiene >= 30 && myPet.petBar.energy >= 30)
        {
            binding.petreacts.setImageResource(R.drawable.animation_train)
            animationDrawable = binding.petreacts.drawable as AnimationDrawable
            animationDrawable.start()
            animationDrawablePet.start()

            Handler(mainLooper).postDelayed(
                { binding.petreacts.setImageResource(0)
                    animationDrawablePet.stop()                },
                2000
            ) // 2 seconds


            myPet.petBar.train += 5
            myPet.petBar.energy -= 10
            myPet.petBar.hygiene -= 3
            databaseHandler.updatePetBar(myPet.petBar)

            if (money)
            {
                myPet.money += 8
            }


            myPet.petStatus.exp += addExp

            if(myPet.petStatus.exp >= 200)
            {
                myPet.petStatus.exp = 0
                myPet.level += 1
                myPet.petStatus.points += 3
            }

            when (itemType) {
                1 -> {
                    myPet.petInventory.boomerang -= 1

                    if (myPet.petInventory.boomerang <=0) {
                        myPet.petInventory.boomerang = 0
                        binding.inventoryitem2.visibility = View.INVISIBLE
                    }
                }
                2 -> {
                    myPet.petInventory.bow -= 1

                    if (myPet.petInventory.bow <=0) {
                        myPet.petInventory.bow = 0
                        binding.inventoryitem3.visibility = View.INVISIBLE
                    }
                }
                3 -> {
                    myPet.petInventory.sword -= 1

                    if (myPet.petInventory.sword <=0) {
                        myPet.petInventory.sword = 0
                        binding.inventoryitem4.visibility = View.INVISIBLE
                    }
                }
            }

            databaseHandler.updatePetInfo(myPet)
            databaseHandler.updatePetStatus(myPet.petStatus)
            databaseHandler.updatePetInventory(myPet.petInventory)
            binding.trainbar.progress = myPet.petBar.train
            binding.hygienebar.progress = myPet.petBar.hygiene
            binding.energybar.progress = myPet.petBar.energy
        }

        else if(myPet.petBar.energy < 30)
        {
            Toast.makeText(this,"Not Enough Energy!",Toast.LENGTH_SHORT).show()
        }

        else if(myPet.petBar.hygiene < 30)
        {
            Toast.makeText(this,"Not Enough Hygiene!",Toast.LENGTH_SHORT).show()
        }
    }

    /*

        Double Tap
     */


    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        Log.d(TAG,"onSingleTapConfirmed: called.")
        return false
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        Log.d(TAG,"onDoubleTap: called.")
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        Log.d(TAG,"onDoubleTapEvent: called.")
        return false
    }
}

