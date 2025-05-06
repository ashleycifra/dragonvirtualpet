package ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.DRAG_FLAG_OPAQUE
import androidx.appcompat.app.AppCompatActivity
import ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor.databinding.ActivityChooseEggBinding
import kotlin.random.Random


class ChooseEgg : AppCompatActivity(), GestureDetector.OnGestureListener, View.OnTouchListener
 {

    private lateinit var binding: ActivityChooseEggBinding
    private lateinit var mDetector: GestureDetector
     private val TAG:String = "ChooseEgg"
     private lateinit var animationDrawable: AnimationDrawable
     private var databaseHandler = DatabaseHandler(this)


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseEggBinding.inflate(layoutInflater)
        setContentView(binding.root)



        mDetector = GestureDetector(this,this)


        binding.eggYellow.setOnTouchListener(this)
        binding.eggRed.setOnTouchListener(this)

        binding.nextBtn.setOnClickListener {

            val goToChooseName = Intent(this,ChooseName::class.java)
            startActivity(goToChooseName)
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            finish()

        }

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

     override fun onFling(
         e1: MotionEvent,
         e2: MotionEvent,
         velocityX: Float,
         velocityY: Float
     ): Boolean {
         Log.d(TAG,"onFling: called. x: $velocityX y: $velocityY")
         return false
     }

     override fun onLongPress(e: MotionEvent) {
         Log.d(TAG,"onLongPress: called.")

         val random = (0..1).random()
         var gender = ""




         if(random == 0)
         {
             gender = "Male"
         }

         else if(random == 1)
         {
             gender = "Female"
         }


            //Yellow
         if(e.source == 1)
         {




             binding.eggYellow.setImageResource(R.drawable.animation_young_brass_dragon)
             animationDrawable = binding.eggYellow.drawable as AnimationDrawable
             animationDrawable.start()


             databaseHandler.addGender(gender)
             databaseHandler.addEggType("yellow")

             binding.nextBtn.visibility = View.VISIBLE
             binding.nextBtn.isEnabled = true

         }



         //Red
         if(e.source == 2)
         {


             binding.eggRed.setImageResource(R.drawable.animation_young_red_dragon)
             animationDrawable = binding.eggRed.drawable as AnimationDrawable
             animationDrawable.start()


             databaseHandler.addGender(gender)
             databaseHandler.addEggType("red")

             binding.nextBtn.visibility = View.VISIBLE
             binding.nextBtn.isEnabled = true
         }

     }



     override fun onTouch(v: View?, event: MotionEvent?): Boolean {


         if (v != null) {
             if(v.id == binding.eggYellow.id && binding.nextBtn.visibility == View.INVISIBLE)
             {
                 if (event != null) {
                     event.source = 1
                     mDetector.onTouchEvent(event)

                 }
                 return true

             }
             if(v.id == binding.eggRed.id)
             {
                 if (event != null && binding.nextBtn.visibility == View.INVISIBLE) {

                     event.source= 2
                     mDetector.onTouchEvent(event)

                 }

                 return true
             }

         }

        return  true
     }





 }