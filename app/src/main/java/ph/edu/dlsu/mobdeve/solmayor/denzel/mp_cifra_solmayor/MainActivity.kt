package ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*



class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var animationDrawableAgd: AnimationDrawable
    private lateinit var animationDrawableAwd: AnimationDrawable
    private lateinit var animationDrawablePw: AnimationDrawable
    private lateinit var animationDrawableVd: AnimationDrawable
    private lateinit var animationDrawableAd: AnimationDrawable
    private var databaseHandler = DatabaseHandler(this)
    private lateinit var myPet: PetInfo
    private lateinit var time:String
    private lateinit var dateCreated: String





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myPet = databaseHandler.getPetInfo()



        if(myPet.name.isNotEmpty())
        {
            myPet.petBar = databaseHandler.getPetBar()

            time = databaseHandler.getTime()
            val timeCalendar = Calendar.getInstance()
            val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault())
            val date = formatter.parse(time)
            timeCalendar.time = date!!

            dateCreated = databaseHandler.getDateCreated()
            val dateCreatedCalendar = Calendar.getInstance()
            val dateDC = formatter.parse(dateCreated)
            dateCreatedCalendar.time = dateDC!!



            //For age formula
            val diff: Int = Calendar.getInstance().get(Calendar.DATE) - dateCreatedCalendar.get(Calendar.DATE)
            myPet.age = diff + 1


            databaseHandler.updatePetInfo(myPet)


            val minDiff: Long = Calendar.getInstance().time.time - timeCalendar.time.time
            val seconds = minDiff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24

            if(minutes >=3)
            {
                var timeElapsed = minutes.toInt() * 10

                myPet.petBar.energy += timeElapsed
                if(myPet.petBar.energy > 100)
                {
                    myPet.petBar.energy = 100
                }

                myPet.petBar.hygiene -= timeElapsed
                if(myPet.petBar.hygiene < 0)
                {
                    myPet.petBar.hygiene = 0
                }

                myPet.petBar.hunger -= timeElapsed
                if(myPet.petBar.hunger < 0)
                {
                    myPet.petBar.hunger = 0

                }
                myPet.petBar.train -= timeElapsed
                if(myPet.petBar.train < 0)
                {
                    myPet.petBar.train = 0
                }

                myPet.petBar.play -= timeElapsed
                if(myPet.petBar.play < 0)
                {
                    myPet.petBar.play = 0
                }
                databaseHandler.updatePetBar(myPet.petBar)
            }



            updateTime()

        }











        animationDrawableAgd = binding.animationAgd.drawable as AnimationDrawable
        animationDrawableAwd = binding.animationAwd.drawable as AnimationDrawable
        animationDrawablePw = binding.animationPw.drawable as AnimationDrawable
        animationDrawableVd = binding.animationVd.drawable as AnimationDrawable
        animationDrawableAd = binding.animationAd.drawable as AnimationDrawable




        animationDrawableAgd.start()
        animationDrawableAwd.start()
        animationDrawablePw.start()
        animationDrawableVd.start()
        animationDrawableAd.start()


        binding.startBtn.setOnClickListener{



            if(myPet.name.isNotEmpty()) {

                val goToHomeScreen = Intent(this,HomeScreen::class.java)


                startActivity(goToHomeScreen)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }

            else{
                val goToChooseEgg = Intent(this, ChooseEgg::class.java)

                startActivity(goToChooseEgg)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

                finish()
            }



        }


    }



    override fun onDestroy() {
        if(myPet.name.isNotEmpty())
        {
            updateTime()
        }

        super.onDestroy()
    }

    fun updateTime()
    {
        var currentTime = Calendar.getInstance().time.toString()
        databaseHandler.updateTime(currentTime)
    }



}