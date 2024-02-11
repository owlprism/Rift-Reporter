package com.example.lol_project.models

import java.io.Serializable

/* DATA
* {
* "id":"Z9ljf1OpOloHnK4IUThlXeJCo0fjkfnqWeZyZgwAM0Wa6_q7QvzATypkJQ",
* "accountId":"QMTo5b2Z8HS-JeFcJFp8PwYJntYRQRbcGyIxH-mC5e1ld8VGUmaT_tzq",
* "puuid":"XZAlgK-fv00uAtryNFoYFz7X2Fv5nKyDImD4TBmAaEXOLadRwa-GIRFdrjOe7ENwZDL7jki07ShwYg",
* "name":"Owlprism",
* "profileIconId":23,
* "revisionDate":1697063710000,
* "summonerLevel":18
* }
* */

data class PostModelProfile (
    val id : String? = null,
    val accountId : String? = null,
    val puuid : String? = null,
    val name : String? = null,
    val profileIconId : Int? = null,
    val revisionDate : Long? = null,
    val summonerLevel : Long? = null
) : Serializable