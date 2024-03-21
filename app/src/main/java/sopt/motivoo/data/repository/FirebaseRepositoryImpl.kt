package sopt.motivoo.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import sopt.motivoo.di.IoDispatcher
import sopt.motivoo.domain.repository.FirebaseRepository
import sopt.motivoo.util.Constants.USERS
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseRealtimeDatabase: FirebaseDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : FirebaseRepository {
    override suspend fun getStepCount(id: Long): Long? {
        return CoroutineScope(ioDispatcher).async {
            (firebaseRealtimeDatabase.reference.child(USERS).child(id.toString()).get().await().value as? Long)
        }.await()
    }

    override fun getUpdatedStepCount(otherId: Long): Flow<Int> = callbackFlow<Int> {
        val ref = firebaseRealtimeDatabase.reference.child(USERS)
        val listener = ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot.child(otherId.toString()).getValue(Int::class.java) ?: return)
            }

            override fun onCancelled(error: DatabaseError) {
                cancel()
            }
        })
        ref.addValueEventListener(listener)

        awaitClose {
            ref.removeEventListener(listener)
        }
    }

    override fun setUserStepCount(userId: Long, myStepCount: Int) {
        firebaseRealtimeDatabase.reference.child(USERS).child(userId.toString())
            .setValue(myStepCount)
    }
}
