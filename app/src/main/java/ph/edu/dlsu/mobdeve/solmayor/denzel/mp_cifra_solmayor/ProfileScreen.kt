package ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor.databinding.ActivityProfileScreenBinding
import java.util.*

class ProfileScreen : AppCompatActivity() {

    private lateinit var binding:ActivityProfileScreenBinding
    private lateinit var myPet:PetInfo
    private var databaseHandler = DatabaseHandler(this)
    private lateinit var timer: CountDownTimer
    private var ctr: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityProfileScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myPet = databaseHandler.getPetInfo()
        myPet.petStatus = databaseHandler.getPetStatus()
        myPet.petBar = databaseHandler.getPetBar()


        val bundle =intent.extras

        ctr = bundle!!.getInt("ctr")


//        Log.d("ctr", ctr.toString())
        startTimer(ctr)

        binding.nameinput.text = myPet.name
        binding.ageinput.text = myPet.age.toString()
        binding.levelinput.text = myPet.level.toString()
        binding.genderinput.text = myPet.gender
        binding.pointsnumber.text = myPet.petStatus.points.toString()
        binding.strengthpts.text = myPet.petStatus.strength.toString()
        binding.healthpts.text = myPet.petStatus.health.toString()
        binding.speedpts.text = myPet.petStatus.speed.toString()
        binding.expbar.progress = myPet.petStatus.exp

        if(myPet.age == 1)
        {
            binding.dayheader.text = "day"
        }

        else{
            binding.dayheader.text = "days"
        }

        if(myPet.eggType== "red")
        {
            if(myPet.stage == "baby")
            {
                binding.dragonicon.setImageResource(R.drawable.animation_young_red_dragon)
            }
            else if(myPet.stage == "adult")
            {
                binding.dragonicon.setImageResource(R.drawable.animation_adult_red_dragon)
            }

            else if(myPet.stage == "great")
            {
                binding.dragonicon.setImageResource(R.drawable.animation_great_red_wyrm)
            }

        }

        else if(myPet.eggType == "yellow")
        {
            if(myPet.stage == "baby")
            {
                binding.dragonicon.setImageResource(R.drawable.animation_young_brass_dragon)
            }
            else if(myPet.stage == "adult")
            {
                binding.dragonicon.setImageResource(R.drawable.animation_adult_brass_dragon)
            }

            else if(myPet.stage == "great")
            {
                binding.dragonicon.setImageResource(R.drawable.animation_great_golden_wyrm)
            }
        }

        if(myPet.stage == "great")
        {
            binding.evolveBtn.visibility = View.GONE
        }

            if(myPet.stage == "baby" && myPet.level >= 15)
            {
                binding.evolveBtn.visibility = View.VISIBLE
            }

                if(myPet.stage == "adult" && myPet.level >= 30)
                {
                    binding.evolveBtn.visibility = View.VISIBLE
                }
        if(myPet.petStatus.points != 0)
        {
            binding.strengthminus.visibility = View.VISIBLE
            binding.speedminus.visibility = View.VISIBLE
            binding.healthminus.visibility = View.VISIBLE

            binding.strengthplus.visibility = View.VISIBLE
            binding.speedplus.visibility = View.VISIBLE
            binding.healthplus.visibility = View.VISIBLE
        }


        //STRENGTH STAT
        binding.strengthplus.setOnClickListener {

            if(myPet.petStatus.points !=0)
            {
                myPet.petStatus.strength +=1
                myPet.petStatus.points -=1
                databaseHandler.updatePetStatus(myPet.petStatus)
            }

            if(myPet.petStatus.points == 0)
            {
                statButtonsInvisible()
            }


            binding.pointsnumber.text = myPet.petStatus.points.toString()
            binding.strengthpts.text = myPet.petStatus.strength.toString()
        }

        binding.strengthminus.setOnClickListener {

            if(myPet.petStatus.strength >=1 && myPet.petStatus.points !=0)
            {
                myPet.petStatus.strength -=1
                myPet.petStatus.points +=1
                databaseHandler.updatePetStatus(myPet.petStatus)
            }

            binding.pointsnumber.text = myPet.petStatus.points.toString()
            binding.strengthpts.text = myPet.petStatus.strength.toString()

        }

        //SPEED STAT
        binding.speedplus.setOnClickListener {

            if(myPet.petStatus.points !=0)
            {
                myPet.petStatus.speed +=1
                myPet.petStatus.points -=1
                databaseHandler.updatePetStatus(myPet.petStatus)
            }

            if(myPet.petStatus.points == 0)
            {
                statButtonsInvisible()
            }

            binding.pointsnumber.text = myPet.petStatus.points.toString()
            binding.speedpts.text = myPet.petStatus.speed.toString()
        }

        binding.speedminus.setOnClickListener {

            if(myPet.petStatus.speed >=1 && myPet.petStatus.points !=0)
            {
                myPet.petStatus.speed -=1
                myPet.petStatus.points +=1
                databaseHandler.updatePetStatus(myPet.petStatus)
            }

            binding.pointsnumber.text = myPet.petStatus.points.toString()
            binding.speedpts.text = myPet.petStatus.speed.toString()

        }

        //HEALTH STAT
        binding.healthplus.setOnClickListener {

            if(myPet.petStatus.points !=0)
            {
                myPet.petStatus.health +=1
                myPet.petStatus.points -=1
                databaseHandler.updatePetStatus(myPet.petStatus)
            }

            if(myPet.petStatus.points == 0)
            {
                statButtonsInvisible()
            }

            binding.pointsnumber.text = myPet.petStatus.points.toString()
            binding.healthpts.text = myPet.petStatus.health.toString()
        }

        binding.healthminus.setOnClickListener {

            if(myPet.petStatus.health >=1 && myPet.petStatus.points !=0)
            {
                myPet.petStatus.health -=1
                myPet.petStatus.points +=1
                databaseHandler.updatePetStatus(myPet.petStatus)
            }

            binding.pointsnumber.text = myPet.petStatus.points.toString()
            binding.healthpts.text = myPet.petStatus.health.toString()

        }







        //NAV BAR

        binding.homenav.setOnClickListener {
            val goToHomeScreen = Intent(this,HomeScreen::class.java)


            val bundle = Bundle()

            timer.cancel()
            bundle.putInt("ctr",ctr)


            goToHomeScreen.putExtras(bundle)
            startActivity(goToHomeScreen)
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            finish()

        }

        binding.shopnav.setOnClickListener {
            val goToStore = Intent(this,StoreScreen::class.java)
            val bundle = Bundle()

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

            timer.cancel()
            bundle.putInt("ctr",ctr)

            goToArenaScreen.putExtras(bundle)
            startActivity(goToArenaScreen)
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            finish()
        }

        binding.evolveBtn.setOnClickListener {
            val goToEvolve = Intent(this,EvolutionScreen::class.java)



            val bundle = Bundle()

            timer.cancel()
            bundle.putInt("ctr",ctr)


            goToEvolve.putExtras(bundle)
            startActivity(goToEvolve)
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            finish()

        }


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




    fun statButtonsInvisible()
    {
        binding.strengthminus.visibility = View.INVISIBLE
        binding.speedminus.visibility = View.INVISIBLE
        binding.healthminus.visibility = View.INVISIBLE

        binding.strengthplus.visibility = View.INVISIBLE
        binding.speedplus.visibility = View.INVISIBLE
        binding.healthplus.visibility = View.INVISIBLE
    }
}