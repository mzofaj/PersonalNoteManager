package eu.funkysoft.personalnotes.Api.Models

import eu.funkysoft.personalnotes.Tools.Const
import java.io.Serializable

class Note : Serializable{

    var id:Int = Const.NUL
    var title:String = Const.EMPTY
}