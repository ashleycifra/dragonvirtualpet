package ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor.databinding.ActivityStoreScreenBinding
import java.util.*

class StoreScreen : AppCompatActivity() {
    private lateinit var binding:ActivityStoreScreenBinding
    private lateinit var myPet:PetInfo
    private var databaseHandler = DatabaseHandler(this)
    private lateinit var timer: CountDownTimer
    private var ctr: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        myPet = databaseHandler.getPetInfo()
        myPet.petBar = databaseHandler.getPetBar()
        myPet.petInventory = databaseHandler.getPetInventory()


        ctr = bundle!!.getInt("ctr")


//        Log.d("ctr", ctr.toString())
        startTimer(ctr)

        binding.moneytext.text = myPet.money.toString()

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


        binding.profilenav.setOnClickListener {
            val goToProfile = Intent(this,ProfileScreen::class.java)


            val bundle = Bundle()

            timer.cancel()
            bundle.putInt("ctr",ctr)


            goToProfile.putExtras(bundle)
            startActivity(goToProfile)
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



        binding.foodbar.setOnClickListener {

            binding.foodbar.tag="clicked"
            binding.toybar.tag="unclicked"
            binding.trainbar.tag="unclicked"

            binding.foodbar.setBackgroundResource(R.color.darkerlightyellow)
            binding.toybar.setBackgroundResource(R.color.lightyellow)
            binding.trainbar.setBackgroundResource(R.color.lightyellow)

            binding.item1icon.setImageResource(R.drawable.foodstore_apple)
            binding.item1title.text = "Apple"
            binding.item1description.text ="+8 exp"

            binding.item2icon.setImageResource(R.drawable.foodstore_bread)
            binding.item2title.text ="Bread"
            binding.item2description.text ="+10 exp"

            binding.item3icon.setImageResource(R.drawable.foodstore_cake)
            binding.item3title.text = "Cake"
            binding.item3description.text ="+12 exp"
        }

        binding.toybar.setOnClickListener {


            binding.foodbar.tag="unclicked"
            binding.toybar.tag="clicked"
            binding.trainbar.tag="unclicked"

            binding.toybar.setBackgroundResource(R.color.darkerlightyellow)
            binding.foodbar.setBackgroundResource(R.color.lightyellow)
            binding.trainbar.setBackgroundResource(R.color.lightyellow)

            binding.item1icon.setImageResource(R.drawable.toystore_bat)
            binding.item1title.text = "Bat"
            binding.item1description.text ="+15 exp"

            binding.item2icon.setImageResource(R.drawable.toystore_drums)
            binding.item2title.text ="Drums"
            binding.item2description.text ="+18 exp"

            binding.item3icon.setImageResource(R.drawable.toystore_tv)
            binding.item3title.text = "TV"
            binding.item3description.text ="+21 exp"

        }

        binding.trainbar.setOnClickListener {

            binding.foodbar.tag="unclicked"
            binding.toybar.tag="unclicked"
            binding.trainbar.tag="clicked"

            binding.trainbar.setBackgroundResource(R.color.darkerlightyellow)
            binding.toybar.setBackgroundResource(R.color.lightyellow)
            binding.foodbar.setBackgroundResource(R.color.lightyellow)

            binding.item1icon.setImageResource(R.drawable.trainstore_boomerang)
            binding.item1title.text = "Boomerang"
            binding.item1description.text ="+20 exp"

            binding.item2icon.setImageResource(R.drawable.trainstore_bow)
            binding.item2title.text ="Bow"
            binding.item2description.text ="+23 exp"

            binding.item3icon.setImageResource(R.drawable.trainstore_sword)
            binding.item3title.text = "Sword"
            binding.item3description.text ="+27 exp"

        }



        binding.petlayout1.setOnClickListener {

            if(myPet.money >= 25)
            {
                if(binding.foodbar.tag == "clicked")
                {
                myPet.petInventory.apple +=1

                Toast.makeText(applicationContext,"Bought 1 Apple!",Toast.LENGTH_SHORT).show()

                }

                if(binding.toybar.tag == "clicked")
                {
                    myPet.petInventory.bat +=1


                    Toast.makeText(applicationContext,"Bought 1 Bat!",Toast.LENGTH_SHORT).show()

                }

                if(binding.trainbar.tag == "clicked")
                {
                    myPet.petInventory.boomerang +=1


                    Toast.makeText(applicationContext,"Bought 1 Boomerang!",Toast.LENGTH_SHORT).show()

                }


                myPet.money -= 25
                binding.moneytext.text = myPet.money.toString()
                databaseHandler.updatePetInfo(myPet)
                databaseHandler.updatePetInventory(myPet.petInventory)
            }

            else
            {
                Toast.makeText(applicationContext,"Not Enough Money!",Toast.LENGTH_SHORT).show()
            }


        }


        binding.petlayout2.setOnClickListener {

            if(myPet.money >= 50)
            {
                if(binding.foodbar.tag == "clicked")
                {
                    myPet.petInventory.bread +=1

                    Toast.makeText(applicationContext,"Bought 1 Bread!",Toast.LENGTH_SHORT).show()

                }

                if(binding.toybar.tag == "clicked")
                {
                    myPet.petInventory.drums +=1


                    Toast.makeText(applicationContext,"Bought 1 Drums!",Toast.LENGTH_SHORT).show()

                }

                if(binding.trainbar.tag == "clicked")
                {
                    myPet.petInventory.bow +=1


                    Toast.makeText(applicationContext,"Bought 1 Bow!",Toast.LENGTH_SHORT).show()

                }


                myPet.money -= 50
                binding.moneytext.text = myPet.money.toString()
                databaseHandler.updatePetInfo(myPet)
                databaseHandler.updatePetInventory(myPet.petInventory)

            }

            else
            {
                Toast.makeText(applicationContext,"Not Enough Money!",Toast.LENGTH_SHORT).show()
            }
        }


        binding.petlayout3.setOnClickListener {

            if(myPet.money >= 75)
            {
                if(binding.foodbar.tag == "clicked")
                {
                    myPet.petInventory.cake +=1

                    Toast.makeText(applicationContext,"Bought 1 Bread!",Toast.LENGTH_SHORT).show()

                }

                if(binding.toybar.tag == "clicked")
                {
                    myPet.petInventory.tv +=1


                    Toast.makeText(applicationContext,"Bought 1 Drums!",Toast.LENGTH_SHORT).show()

                }

                if(binding.trainbar.tag == "clicked")
                {
                    myPet.petInventory.sword +=1


                    Toast.makeText(applicationContext,"Bought 1 Bow!",Toast.LENGTH_SHORT).show()

                }


                myPet.money -= 75
                binding.moneytext.text = myPet.money.toString()
                databaseHandler.updatePetInfo(myPet)
                databaseHandler.updatePetInventory(myPet.petInventory)

            }

            else
            {
                Toast.makeText(applicationContext,"Not Enough Money!",Toast.LENGTH_SHORT).show()
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



}