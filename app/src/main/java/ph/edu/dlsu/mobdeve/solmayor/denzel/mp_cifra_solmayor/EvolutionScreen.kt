package ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor.databinding.ActivityEvolutionScreenBinding
import java.util.*

class EvolutionScreen : AppCompatActivity(),
                        View.OnTouchListener,
                        GestureDetector.OnDoubleTapListener,
                        GestureDetector.OnGestureListener{
    private val TAG:String = "EvolutionScreen"
     private lateinit var binding:ActivityEvolutionScreenBinding
    private lateinit var myPet:PetInfo
    private var animationDrawable = AnimationDrawable()
    private lateinit var animationDrawableEvolve: AnimationDrawable
    private lateinit var mDetector: GestureDetector
    private var databaseHandler = DatabaseHandler(this)
    private lateinit var timer: CountDownTimer
    private var ctr: Int = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEvolutionScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDetector = GestureDetector(this,this)
        binding.petevolve.setOnTouchListener(this)


        myPet = databaseHandler.getPetInfo()
        myPet.petBar = databaseHandler.getPetBar()



        val bundle =intent.extras

        ctr = bundle!!.getInt("ctr")

//        Log.d("ctr", ctr.toString())
        startTimer(ctr)


        binding.backBtn.setOnClickListener {

            val goToHomeScreen = Intent(this,HomeScreen::class.java)

            val bundle = Bundle()

            timer.cancel()
            bundle.putInt("ctr",ctr)


            goToHomeScreen.putExtras(bundle)
            startActivity(goToHomeScreen)
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            finish()
        }

        if(myPet.stage == "baby")
        {
           if(myPet.eggType == "red")
           {
               val dp = 80f
         val px = dp * resources.displayMetrics.density


          binding.petevolve.layoutParams.height = px.toInt()
           binding.petevolve.layoutParams.width = px.toInt()
           binding.petevolve.requestLayout()
               binding.petevolve.setImageResource(R.drawable.animation_young_red_dragon)
               binding.petevolve.tag = "red"

               animationDrawable = binding.petevolve.drawable as AnimationDrawable
               animationDrawable.start()

           }

            else if(myPet.eggType == "yellow")
           {
               val dp = 80f
               val px = dp * resources.displayMetrics.density
               binding.petevolve.layoutParams.height = px.toInt()
               binding.petevolve.layoutParams.width = px.toInt()
               binding.petevolve.requestLayout()
               binding.petevolve.setImageResource(R.drawable.animation_young_brass_dragon)

               binding.petevolve.tag = "yellow"

               animationDrawable = binding.petevolve.drawable as AnimationDrawable
               animationDrawable.start()
           }
        }


        else if(myPet.stage == "adult")
        {
            if(myPet.eggType == "red")
            {
                val dp = 120f
                val px = dp * resources.displayMetrics.density

                binding.petevolve.layoutParams.height = px.toInt()
                binding.petevolve.layoutParams.width = px.toInt()
                binding.petevolve.requestLayout()
                binding.petevolve.setImageResource(R.drawable.animation_adult_red_dragon)
                binding.petevolve.tag = "red"

                animationDrawable = binding.petevolve.drawable as AnimationDrawable
                animationDrawable.start()

            }

            else if(myPet.eggType == "yellow")
            {
                val dp = 120f
                val px = dp * resources.displayMetrics.density
                binding.petevolve.layoutParams.height = px.toInt()
                binding.petevolve.layoutParams.width = px.toInt()
                binding.petevolve.requestLayout()
                binding.petevolve.setImageResource(R.drawable.animation_adult_brass_dragon)

                binding.petevolve.tag = "yellow"

                animationDrawable = binding.petevolve.drawable as AnimationDrawable
                animationDrawable.start()
            }
        }


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

                databaseHandler.updatePetBar(myPet.petBar)
                startTimer(0)
            }

        }.start()




    }

    override fun onDestroy() {

        updateTime()
        super.onDestroy()
    }

    fun updateTime()
    {
        var currentTime = Calendar.getInstance().time.toString()
        databaseHandler.updateTime(currentTime)
    }







    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        if (event != null) {
            mDetector.onTouchEvent(event)
            return true
        }

        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        Log.d(TAG,"onSingleTapConfirmed: called.")
        return false
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        Log.d(TAG,"onDoubleTap: called.")

        if(binding.doubletaptext.visibility == View.VISIBLE)
        {
            // Red Evolution
            if(myPet.eggType == "red")
            {
                if(myPet.stage == "baby")
                {
                    binding.petevolve.setImageResource(R.drawable.animation_evolution)
                    animationDrawable = binding.petevolve.drawable as AnimationDrawable
                    animationDrawable.start()
                    binding.doubletaptext.visibility = View.INVISIBLE

                    Handler(mainLooper).postDelayed(
                        { val dp = 120f
                            val px = dp * resources.displayMetrics.density
                            binding.petevolve.layoutParams.height = px.toInt()
                            binding.petevolve.layoutParams.width = px.toInt()
                            binding.petevolve.requestLayout()
                            binding.petevolve.setImageResource(R.drawable.animation_adult_red_dragon)



                            Handler(mainLooper).postDelayed(
                                { binding.backBtn.visibility = View.VISIBLE             },
                                1000
                            )

                            animationDrawable = binding.petevolve.drawable as AnimationDrawable
                            animationDrawable.start()            },
                        4000
                    ) // 4 seconds

                    myPet.stage = "adult"
                    databaseHandler.updatePetInfo(myPet)

                }

                else if(myPet.stage == "adult") {
                    binding.petevolve.setImageResource(R.drawable.animation_evolution)
                    animationDrawable = binding.petevolve.drawable as AnimationDrawable
                    animationDrawable.start()
                    binding.doubletaptext.visibility = View.INVISIBLE

                    Handler(mainLooper).postDelayed(
                        {  val dp = 170f
                            val px = dp * resources.displayMetrics.density
                            binding.petevolve.layoutParams.height = px.toInt()
                            binding.petevolve.layoutParams.width = px.toInt()
                            binding.petevolve.requestLayout()
                            binding.petevolve.setImageResource(R.drawable.animation_great_red_wyrm)





                            Handler(mainLooper).postDelayed(
                                { binding.backBtn.visibility = View.VISIBLE             },
                                1000
                            )

                            animationDrawable = binding.petevolve.drawable as AnimationDrawable
                            animationDrawable.start()            },
                        4000
                    ) // 4 seconds

                    myPet.stage = "great"
                    databaseHandler.updatePetInfo(myPet)

                }


            }


            //Yellow Evolution
            else if(myPet.eggType == "yellow")
            {
                if(myPet.stage == "baby")
                {

                    binding.petevolve.setImageResource(R.drawable.animation_evolution)
                    animationDrawable = binding.petevolve.drawable as AnimationDrawable
                    animationDrawable.start()
                    binding.doubletaptext.visibility = View.INVISIBLE

                    Handler(mainLooper).postDelayed(
                        {  val dp = 120f
                            val px = dp * resources.displayMetrics.density
                            binding.petevolve.layoutParams.height = px.toInt()
                            binding.petevolve.layoutParams.width = px.toInt()
                            binding.petevolve.requestLayout()
                            binding.petevolve.setImageResource(R.drawable.animation_adult_brass_dragon)



                            Handler(mainLooper).postDelayed(
                                { binding.backBtn.visibility = View.VISIBLE             },
                                1000
                            )

                            animationDrawable = binding.petevolve.drawable as AnimationDrawable
                            animationDrawable.start()       },
                        4000
                    ) // 4 seconds
                    myPet.stage = "adult"
                    databaseHandler.updatePetInfo(myPet)

                }

                else if(myPet.stage == "adult") {

                    binding.petevolve.setImageResource(R.drawable.animation_evolution)
                    animationDrawable = binding.petevolve.drawable as AnimationDrawable
                    animationDrawable.start()
                    binding.doubletaptext.visibility = View.INVISIBLE


                    Handler(mainLooper).postDelayed(
                        {  val dp = 170f
                            val px = dp * resources.displayMetrics.density
                            binding.petevolve.layoutParams.height = px.toInt()
                            binding.petevolve.layoutParams.width = px.toInt()
                            binding.petevolve.requestLayout()
                            binding.petevolve.setImageResource(R.drawable.animation_great_golden_wyrm)




                            Handler(mainLooper).postDelayed(
                                { binding.backBtn.visibility = View.VISIBLE             },
                                1000
                            )

                            animationDrawable = binding.petevolve.drawable as AnimationDrawable
                            animationDrawable.start()           },
                        4000
                    ) // 4 seconds

                    myPet.stage = "great"
                    databaseHandler.updatePetInfo(myPet)


                }
            }
        }




        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        Log.d(TAG,"onDoubleTapEvent: called.")
        return false
    }

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
        Log.d(TAG,"onLongPress: called.")

    }

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.d(TAG,"onFling: called.")
        return false
    }

}