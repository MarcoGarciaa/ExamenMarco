package com.example.examenmarco

import android.database.sqlite.SQLiteDatabase

class Personaje(
    private var nombre: String,
    private val raza: Raza,
    private var clase: Clase,
    private var estadoVital: EstadoVital
) {
    private var salud: Int = 0
    private var ataque: Int = 0
    private var experiencia: Int
    private var nivel: Int
    private var suerte: Int
    private var defensa: Int = 0

    // Enumeración para el tipo de raza y clase
    enum class Raza { Humano, Elfo, Enano, Maldito }
    enum class Clase { Brujo, Mago, Guerrero }
    enum class EstadoVital{Anciano, Joven, Adulto}

    private val mochila = Mochila(10) // Ejemplo de peso máximo de la mochila
    // Atributos para el equipo del personaje
    private var arma: Articulo? = null
    private var proteccion: Articulo? = null

    // Inicialización de los atributos tras la construcción del objeto Personaje
    init {
        calcularSalud()
        calcularAtaque()
        calcularDefensa()
        experiencia = 0
        nivel = 1
        suerte = (0..10).random() // Asigna un valor de suerte aleatorio entre 0 y 10
    }

    fun getNombre(): String {
        return nombre
    }
    fun setNombre(nuevoNombre: String) {
        nombre = nuevoNombre
    }
    fun getRaza(): Raza {
        return raza
    }
    fun getSalud(): Int {
        return salud
    }
    fun setSalud(nuevaSalud: Int) {
        salud = nuevaSalud
    }
    fun getAtaque(): Int {
        return ataque
    }
    fun setAtaque(nuevoAtaque: Int) {
        ataque = nuevoAtaque
    }
    fun getClase(): Clase {
        return clase
    }
    fun setClase(nuevaClase: Clase) {
        clase = nuevaClase
    }
    fun getEstadoVital(): EstadoVital {
        return estadoVital
    }
    fun setEstadoVital(nuevoEstadoVital: EstadoVital) {
        estadoVital = nuevoEstadoVital
    }
    fun getExperiencia(): Int {
        return experiencia
    }
    fun setExperiencia(experienciaGanada: Int) {
        experiencia += experienciaGanada
        while (experiencia >= 1000) {
            subirNivel()
            experiencia -= 1000 // Reducir la experiencia en 1000 al subir de nivel
        }
    }
    fun getNivel(): Int {
        return nivel
    }
    fun subirNivel() {
        if (nivel < 10) { // Limitar el nivel a 10
            nivel++
            calcularSalud() // Calcular el nuevo valor de salud al subir de nivel
            calcularAtaque() // Calcular el nuevo valor de ataque al subir de nivel
            calcularDefensa()
        }
    }
    private fun calcularSalud() {
        salud = when (nivel) {
            1 -> 100
            2 -> 200
            3 -> 300
            4 -> 450
            5 -> 600
            6 -> 800
            7 -> 1000
            8 -> 1250
            9 -> 1500
            10 -> 2000
            else -> 100 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }

    private fun calcularAtaque() {
        ataque = when (nivel) {
            1 -> 10
            2 -> 20
            3 -> 25
            4 -> 30
            5 -> 40
            6 -> 100
            7 -> 200
            8 -> 350
            9 -> 400
            10 -> 450
            else -> 10 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }
    private fun calcularDefensa() {
        defensa = when (nivel) {
            1 -> 4
            2 -> 9
            3 -> 14
            4 -> 19
            5 -> 49
            6 -> 59
            7 -> 119
            8 -> 199
            9 -> 349
            10 -> 399
            else -> 4 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }


    fun habilidad() {
        when (clase) {
            Clase.Mago -> {
                calcularSalud() // Subir la salud al límite del nivel
                println("$nombre utiliza su habilidad de Mago para restaurar su salud.")
            }
            Clase.Brujo -> {
                ataque *= 2 // Duplicar el ataque
                println("$nombre utiliza su habilidad de Brujo para duplicar su ataque.")
            }
            Clase.Guerrero -> {
                suerte *= 2 // Duplicar la suerte
                println("$nombre utiliza su habilidad de Guerrero para duplicar su suerte.")
            }
        }
    }
    fun entrenar(tiempoDeEntrenamiento: Int) {
        val factorExperienciaPorHora = 5
        val experienciaGanada = tiempoDeEntrenamiento * factorExperienciaPorHora

        setExperiencia(experienciaGanada)

        println("$nombre ha entrenado durante $tiempoDeEntrenamiento horas y ha ganado $experienciaGanada de experiencia.")
    }
    fun realizarMision(tipoMision: String, dificultad: String) {
        val probabilidadExito = when (dificultad) {
            "Fácil" -> if (nivel >= 5) 8 else 6
            "Normal" -> if (nivel >= 3) 6 else 4
            "Difícil" -> if (nivel >= 7) 4 else 2
            else -> 0 // En caso de dificultad no reconocida
        }

        val resultado = (1..10).random() // Valor aleatorio entre 1 y 10

        if (resultado <= probabilidadExito) {
            val experienciaGanada = when (tipoMision) {
                "Caza" -> 1000
                "Búsqueda" -> 500
                "Asedio" -> 2000
                "Destrucción" -> 5000
                else -> 0 // En caso de tipo de misión no reconocido
            }

            val multiplicadorExperiencia = when (dificultad) {
                "Fácil" -> 0.5
                "Normal" -> 1.0
                "Difícil" -> 2.0
                else -> 0.0 // En caso de dificultad no reconocida
            }

            val experienciaFinal = (experienciaGanada * multiplicadorExperiencia).toInt()
            setExperiencia(experienciaFinal)
            println("$nombre ha completado la misión de $tipoMision ($dificultad) con éxito y gana $experienciaFinal de experiencia.")
        } else {
            println("$nombre ha fracasado en la misión de $tipoMision ($dificultad) y no recibe ninguna recompensa.")
        }
    }
    fun cifrado(mensaje : String, ROT : Int) : String{
        val abecedario : ArrayList<Char> = "abcdefghijklmnñopqrstuvwxyz".toList() as ArrayList<Char>
        var stringInv = ""
        var indice = 0

        for(i in mensaje.lowercase().toList() as ArrayList<Char>){
            if(!i.isLetter() || i.isWhitespace()){
                stringInv += i
            } else{
                indice = abecedario.indexOf(i) + ROT
                if (indice >= abecedario.size) {
                    indice -= abecedario.size
                    stringInv += abecedario[indice]
                } else
                    stringInv += abecedario[indice]
            }
        }
        return stringInv.filter { !it.isWhitespace() && it.isLetter() }
    }
    fun comunicacion(mensaje:String){
        var respuesta=""
        when(estadoVital){
            EstadoVital.Adulto->{
                if (mensaje.equals("¿Como estas?"))
                    respuesta="En la flor de la vida, pero me empieza a doler la espalda"
                else
                    if ((mensaje.contains('?') || mensaje.contains('¿')) && mensaje == mensaje.uppercase())
                        respuesta="Estoy buscando la mejor solución"
                    else
                        if (mensaje == mensaje.uppercase())
                            respuesta="No me levantes la voz mequetrefe"
                        else
                            if (mensaje == nombre)
                                respuesta="¿Necesitas algo?"
                            else
                                if(mensaje == "Tienes algo equipado?"){
                                    if (arma != null || proteccion != null) {
                                        val equipamiento = mutableListOf<String>()
                                        if (arma != null) {
                                            equipamiento.add(arma!!.getNombre().name)
                                        }
                                        if (proteccion != null) {
                                            equipamiento.add(proteccion!!.getNombre().name)
                                        }
                                        println("Tengo equipado: ${equipamiento.joinToString(", ")}")
                                    } else {
                                        println("Ligero como una pluma.")
                                    }
                                }
                                else
                                    respuesta="No sé de qué me estás hablando"
            }
            EstadoVital.Joven->{
                if (mensaje.equals("¿Como estas?"))
                    respuesta="De lujo"
                else
                    if ((mensaje.contains('?') || mensaje.contains('¿')) && mensaje == mensaje.uppercase())
                        respuesta="Tranqui se lo que hago"
                    else
                        if (mensaje == mensaje.uppercase())
                            respuesta="Eh relájate"
                        else
                            if (mensaje == nombre)
                                respuesta="Qué pasa?"
                            else
                                if(mensaje == "Tienes algo equipado?"){
                                    if (arma != null || proteccion != null) {
                                        println("No llevo nada, agente, se lo juro.")
                                    } else {
                                        println("Regístrame si quieres.")
                                    }
                                }
                                else
                                    respuesta="Yo que se"

            }
            EstadoVital.Anciano->{
                if (mensaje.equals("¿Como estas?"))
                    respuesta="No me puedo mover"
                else
                    if ((mensaje.contains('?') || mensaje.contains('¿')) && mensaje == mensaje.uppercase())
                        respuesta="Que no te escucho!"
                    else
                        if (mensaje == mensaje.uppercase())
                            respuesta="Háblame más alto que no te escucho"
                        else
                            if (mensaje == nombre)
                                respuesta="Las 5 de la tarde"
                            else
                                if(mensaje == "Tienes algo equipado?"){
                                    println("A ti que te importa nini!")
                                }
                                else
                                    respuesta="En mis tiempos esto no pasaba"
            }
        }
        when(raza){
            Raza.Elfo-> println(cifrado(respuesta, 1))
            Raza.Enano-> println(respuesta.uppercase())
            Raza.Maldito-> println(cifrado(respuesta, 1))
            else -> println(respuesta)
        }
    }
    fun equipar(articulo: Articulo) {
        when (articulo.getTipoArticulo()) {
            Articulo.TipoArticulo.ARMA -> {
                if (articulo.getNombre() in Articulo.Nombre.BASTON..Articulo.Nombre.GARRAS) {
                    arma = articulo
                    // Aumentar el ataque del personaje según el nombre del arma
                    ataque += articulo.getAumentoAtaque()
                    println("Has equipado el arma: $articulo")
                    mochila.getContenido().remove(articulo)
                } else {
                    println("No se puede equipar el artículo. Tipo de arma no válido.")
                }
            }
            Articulo.TipoArticulo.PROTECCION -> {
                when (articulo.getNombre()) {
                    Articulo.Nombre.ESCUDO, Articulo.Nombre.ARMADURA -> {
                        proteccion = articulo
                        // Aumentar la defensa del personaje solo si la protección es un escudo o una armadura
                        defensa += articulo.getAumentoDefensa()
                        println("Has equipado la protección: $articulo")
                        mochila.getContenido().remove(articulo)
                    }
                    else -> {
                        println("No se puede equipar el artículo. Tipo de protección no válido.")
                    }
                }
            }
            else -> {
                println("No se puede equipar el artículo. Tipo de artículo no válido.")
            }
        }
    }
    fun usarObjeto(articulo: Articulo) {
        when (articulo.getTipoArticulo()) {
            Articulo.TipoArticulo.OBJETO -> {
                when (articulo.getNombre()) {
                    Articulo.Nombre.POCION -> {
                        // Aumentar la vida del personaje al usar una poción
                        salud += articulo.getAumentoVida()
                        println("Has usado la poción y aumentado tu vida. Vida actual: $salud")
                        mochila.getContenido().remove(articulo)
                    }
                    Articulo.Nombre.IRA -> {
                        // Aumentar el ataque del personaje al usar un objeto de ira
                        ataque += articulo.getAumentoAtaque()
                        println("Has canalizado tu ira y aumentado tu ataque. Ataque actual: $ataque")
                        mochila.getContenido().remove(articulo)
                    }
                    else -> {
                        println("No se puede usar el objeto. Tipo de objeto no válido.")
                    }
                }
            }
            else -> {
                println("No se puede usar el artículo. Tipo de artículo no válido.")
            }
        }
    }
    fun getMochila(): Mochila {
        return this.mochila
    }

    override fun toString(): String {
        return "Personaje: Nombre: $nombre, Nivel: $nivel, Salud: $salud, Ataque: $ataque, Defensa: $defensa, Suerte: $suerte, Raza: $raza, Clase: $clase, Estado Vital: $estadoVital Mochila: $mochila"
    }

}
/***********************************************************************************************************************
 *  CLASE: Mochila
 *  CONSTRUCTOR:
 *      pesoMochila      - > Peso máximo que puede soportar la mochila (Int)
 *
 *  METODOS
 *      getPesoMochila()        - > Devuelve el peso máximo como Int
 *      addArticulo()           - > Añade un artículo (clase Articulo) a la mochila, comprobando que el peso del
 *                                  artículo sumado al peso total del resto de artículos de la Mochila no supere el
 *                                  límite (pesoMochila)
 *      getContenido()          - > Devuelve el ArrayList de artículos que contiene la mochila
 *      findObjeto(nombre)      - > Devuelve la posición del artículo que cuyo nombre (Articulo.Nombre) pasamos como
 *                                  entrada o -1 si no lo encuentra
 *
 **********************************************************************************************************************/
class Mochila(private var pesoMochila: Int){
    val dbHelper = DatabaseHelper(this)

    fun getArticulos(): ArrayList<Articulo>{
        val articulos = ArrayList<Articulo>()
            val selectQuery = "SELECT * FROM $dbHelper.TABLA_ARTICULOS"
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if(cursor.moveToFirst()){
            do {
                val _id = cursor.getInt(cursor.getColumnIndex(dbHelper.KEY_ID))
                val TipoArticulo  = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_TIPO_ARTICULO))
                val nombre = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_NOMBRE))
                val peso = cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_PESO))
                val precio = cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_PRECIO))

            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return articulos
    }


    fun getNumeroArticulos():Int{
        return getArticulos().count()
    }

    fun addArticulo(articulo :Articulo){
        val db = dbHelper.writableDatabase
        if (articulo.getPeso() <= pesoMochila) {
            when (articulo.getTipoArticulo()) {
                Articulo.TipoArticulo.ARMA -> {
                    when (articulo.getNombre()) {
                        Articulo.Nombre.BASTON, Articulo.Nombre.ESPADA, Articulo.Nombre.DAGA,
                        Articulo.Nombre.MARTILLO, Articulo.Nombre.GARRAS -> {

                            val values = ContentValues().apply {
                                put(DatabaseHelper.KEY_ID, articulo.getArticulo())
                                put(DatabaseHelper.COLUMN_TIPO_ARTICULO, articulo.getTipoArticulo())
                                put(DatabaseHelper.COLUMN_NOMBRE, articulo.getNombre())
                                put(DatabaseHelper.COLUMN_PESO, articulo.getPeso())
                                put(DatabaseHelper.COLUMN_PRECIO, precio)
                            }
                            db.insert(DatabaseHelper.TABLA_ARTICULOS,null,values)

                            this.pesoMochila -= articulo.getPeso()
                            println("${articulo.getNombre()} ha sido añadido a la mochila.")
                        }
                        else -> println("Nombre del artículo no válido para el tipo ARMA.")
                    }
                }
                Articulo.TipoArticulo.OBJETO -> {
                    when (articulo.getNombre()) {
                        Articulo.Nombre.POCION, Articulo.Nombre.IRA -> {

                            val values = ContentValues().apply {
                                put(DatabaseHelper.KEY_ID, articulo.getArticulo())
                                put(DatabaseHelper.COLUMN_TIPO_ARTICULO, articulo.getTipoArticulo())
                                put(DatabaseHelper.COLUMN_NOMBRE, articulo.getNombre())
                                put(DatabaseHelper.COLUMN_PESO, articulo.getPeso())
                                put(DatabaseHelper.COLUMN_PRECIO, precio)
                            }
                            db.insert(DatabaseHelper.TABLA_ARTICULOS,null,values)

                            this.pesoMochila -= articulo.getPeso()
                            println("${articulo.getNombre()} ha sido añadido a la mochila.")
                        }
                        else -> println("Nombre del artículo no válido para el tipo OBJETO.")
                    }
                }
                Articulo.TipoArticulo.PROTECCION -> {
                    when (articulo.getNombre()) {
                        Articulo.Nombre.ESCUDO, Articulo.Nombre.ARMADURA -> {

                            val values = ContentValues().apply {
                                put(DatabaseHelper.KEY_ID, articulo.getArticulo())
                                put(DatabaseHelper.COLUMN_TIPO_ARTICULO, articulo.getTipoArticulo())
                                put(DatabaseHelper.COLUMN_NOMBRE, articulo.getNombre())
                                put(DatabaseHelper.COLUMN_PESO, articulo.getPeso())
                                put(DatabaseHelper.COLUMN_PRECIO, precio)
                            }
                            db.insert(DatabaseHelper.TABLA_ARTICULOS,null,values)

                            this.pesoMochila -= articulo.getPeso()
                            println("${articulo.getNombre()} ha sido añadido a la mochila.")
                        }
                        else -> println("Nombre del artículo no válido para el tipo PROTECCION.")
                    }
                }
            }
        } else {
            println("El peso del artículo excede el límite de la mochila.")
        }



        db.close()
    }
    fun addArticulo(articulo: Articulo) {

    }
    fun getContenido(): ArrayList<Articulo> {
        return contenido
    }
    fun findObjeto(nombre: Articulo.Nombre): Int {
        return contenido.indexOfFirst { it.getNombre() == nombre }
    }
    override fun toString(): String {
        return if (contenido.isEmpty()) {
            "Mochila vacía"
        } else {
            "Artículos en la mochila: ${contenido.joinToString("\n")}"
        }
    }



}



class DatabaseHelper(context: Mochila): SQLiteDatabase(context, DATABASE, null, DATABASE_VERSION)  {
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE = "Personajes.bd"
        private const val TABLA_ARTICULOS = "Articulos"
        private const val KEY_ID = "_id"
        private const val COLUMN_TIPO_ARTICULO = "TipoArticulo"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_PESO = "peso"
        private const val COLUMN_PRECIO = "precio"
    }

    override fun onCreate(db: SQLiteDatabase){
        val createTable = "CREATE TABLE $TABLA_ARTICULOS ($KEY_ID INTEGER PRIMARY KEY)"+
                "$COLUMN_TIPO_ARTICULO TEXT,$COLUMN_NOMBRE TEXT"+
                "$COLUMN_PESO INTEGER,$COLUMN_PRECIO INTEGER"
        db.execSQL(createTable)
    }
    override  fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int){
        db.execSQL("DROP TABLE IF EXISTS $TABLA_ARTICULOS")
        onCreate(db)

    }


}
/***********************************************************************************************************************
 *  CLASE: Articulo
 *  CONSTRUCTOR:
 *      tipoArticulo  - > Enumeración con valores ARMA, OBJETO, PROTECCION
 *      nombre        - > Enumeración con valores BASTON, ESPADA, DAGA, MARTILLO, GARRAS, POCION, IRA, ESCUDO, ARMADURA
 *      peso          - > Peso del artículo
 *
 *  METODOS
 *      getPeso()           - > Devuelve el peso como Int
 *      getNombre()         - > Devuelve el nombre del artículo
 *      getTipoArticulo()   - > Devuelve el tipo del artículo
 *      toString()          - > Sobreescribimos el método toString para darle formato a la visualización de los
 *                              artículos
 *      getAumentoAtaque()  - > Devuelve el aumento de ataque según el nombre del arma o si el objeto es IRA
 *      getAumentoDefensa() - > Devuelve el aumento de defensa según el nombre de la proteccion
 *      getAumentoVida()    - > Devuelve el aumento de vida si el objeto es POCION
 *
 *
 **********************************************************************************************************************/

class Articulo(private var tipoArticulo: TipoArticulo, private var nombre: Nombre, private var peso: Int) {

    enum class TipoArticulo { ARMA, OBJETO, PROTECCION }
    enum class Nombre { BASTON, ESPADA, DAGA, MARTILLO, GARRAS, POCION, IRA, ESCUDO, ARMADURA }

    fun getPeso(): Int {
        return peso
    }
    fun getNombre(): Nombre {
        return nombre
    }
    fun getTipoArticulo(): TipoArticulo {
        return tipoArticulo
    }
    fun getAumentoAtaque(): Int {
        return when (nombre) {
            Nombre.BASTON -> 10
            Nombre.ESPADA -> 20
            Nombre.DAGA -> 15
            Nombre.MARTILLO -> 25
            Nombre.GARRAS -> 30
            Nombre.IRA -> 80
            else -> 0 // Para otros tipos de armas no especificados
        }
    }
    fun getAumentoDefensa(): Int {
        return when (nombre) {
            Nombre.ESCUDO -> 10
            Nombre.ARMADURA -> 20
            else -> 0 // Para otros tipos de protecciones no especificados
        }
    }
    fun getAumentoVida(): Int {
        return when (nombre) {
            Nombre.POCION -> 100
            else -> 0 // Para otros tipos de objetos no especificados
        }
    }
    override fun toString(): String {
        return "[Tipo Artículo:$tipoArticulo, Nombre:$nombre, Peso:$peso]"
    }
}