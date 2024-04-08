import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

object UzdevumuParvaldnieks {

    private val db = Firebase.firestore
    private val uzdevumiKolekcija = db.collection("uzdevumi")

    fun pievienotUzdevumu(uzdevums: Uzdevums): Uzdevums {
        val uzdevumaKarte = hashMapOf(
            "nosaukums" to uzdevums.nosaukums,
            "krāsa" to uzdevums.krāsa,
            "atgādinājums" to uzdevums.atgādinājums,
            "pabeigts" to uzdevums.pabeigts,
            "laika_zīmogs" to Calendar.getInstance().time
        )

        uzdevumiKolekcija.add(uzdevumaKarte)
            .addOnSuccessListener { dokumentaAtsauce ->
                uzdevums.id = dokumentaAtsauce.id
            }

        return uzdevums
    }

    fun atjauninātUzdevumu(uzdevums: Uzdevums) {
        val uzdevumaDokuments = uzdevumiKolekcija.document(uzdevums.id!!)
        val uzdevumaKarte = hashMapOf(
            "nosaukums" to uzdevums.nosaukums,
            "krāsa" to uzdevums.krāsa,
            "atgādinājums" to uzdevums.atgādinājums,
            "pabeigts" to uzdevums.pabeigts
        )

        uzdevumaDokuments.update(uzdevumaKarte)
    }

    fun dzēstUzdevumu(uzdevumaId: String) {
        uzdevumiKolekcija.document(uzdevumaId).delete()
    }

    fun iegūtUzdevumus(callback: (UzdevumuSaraksts) -> Unit) {
        val uzdevumuSaraksts = mutableListOf<Uzdevums>()
        uzdevumiKolekcija.get()
            .addOnSuccessListener { querySnapshot ->
                for (dokuments in querySnapshot.documents) {
                    val uzdevums = dokuments.toObject(Uzdevums::class.java)
                    uzdevums?.id = dokuments.id
                    uzdevums?.let { uzdevumuSaraksts.add(it) }
                }
                callback(UzdevumuSaraksts(uzdevumuSaraksts))
            }
    }
}
