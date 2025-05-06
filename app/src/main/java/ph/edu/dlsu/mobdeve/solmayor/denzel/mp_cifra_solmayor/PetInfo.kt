package ph.edu.dlsu.mobdeve.solmayor.denzel.mp_cifra_solmayor

import android.os.Parcel
import android.os.Parcelable

class PetInfo() {
    lateinit var name: String
    var age:Int = 0
    var level:Int = 0
    lateinit var gender: String
    lateinit var stage: String
    var money:Int = 0
    lateinit var eggType: String
    lateinit var petStatus: PetStatus
    lateinit var petBar: PetBar
    lateinit var petInventory: PetInventory

}