package com.mortex.mortext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import kotlin.math.abs
import kotlin.math.pow

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun `check parking bill`() {
        assertEquals(2, solutionPoli("aaaagbc"))
    }

    @Test
    fun `check flow`() {
        assertEquals(3, checkFlow())
    }

    fun checkFlow() {
        val m = flowOf(1, 2, 3)
        val s = flowOf("a", "n")

        runBlocking {

            m.combine(s) { num, char -> "$num to $char" }.collect {
                println(it)
            }
//
//            m.zip(s) { num, char -> "$num to $char" }.collect {
//                println(it)
//            }
//
//            val myFlow = flow {
//                for (i in 1..3) {
//                    emit(i)
//                    println("Emitting $i")
//                }
//            }
//
//            myFlow.collect { value ->
////                delay(1000)
//                println("Collected $value")
//            }
        }

    }

    fun solutionPoli(S: String): Int {
        // Create a HashMap to count the frequency of each character
        val charCount = mutableMapOf<Char, Int>()

        // Count occurrences of each character
        for (char in S) {
            charCount[char] = charCount.getOrDefault(char, 0) + 1
        }

        var palindromes = 0

        // Attempt to form 3-letter palindromes
        for ((char1, count1) in charCount) {
            while (charCount[char1]!! >= 2) { // Check if we can use 2 of this character
                var foundMiddle = false
                for ((char2, count2) in charCount) {
                    if (char1 != char2 && count2 > 0) { // Check for a middle character
                        // Found a valid palindrome
                        palindromes++
                        charCount[char1] = charCount[char1]!! - 2 // Use 2 of this character
                        charCount[char2] = charCount[char2]!! - 1 // Use 1 of this character
                        foundMiddle = true
                        break
                    }
                }
                if (!foundMiddle) {
                    break // No valid middle character found
                }
            }
        }

        return palindromes
    }

    fun findPalindrome(S: String): Int {
        val charCount = IntArray(26)


        for (char in S) {
            charCount[char - 'a']++
        }

        var palindromes = 0

        // ساخت پالیندروم‌ها
        first@ for (i in 0..25) {
            whi@ while (charCount[i] >= 2) {
                sec@ for (j in 0..25) {
                    if (charCount[j] > 0 && j != i) {
                        palindromes++
                        charCount[i] -= 2 // استفاده از دو حرف یکسان
                        charCount[j]--    // استفاده از حرف وسط
                        break@sec
                    }
                }
                break@whi
            }
        }

        return palindromes
    }

    private fun findLargestSquare(A: Int, B: Int): Int {
        fun countPieces(length: Int, stick1: Int, stick2: Int): Int {
            return (stick1 / length) + (stick2 / length)
        }

        fun canFormSquare(length: Int, stick1: Int, stick2: Int): Boolean {
            return countPieces(length, stick1, stick2) >= 4
        }

        // Fix: Iterate from the largest possible square size (A+B)/4
        for (length in ((A + B) / 4 downTo 1)) {
            if (canFormSquare(length, A, B)) {
                return length
            }
        }

        return 0
    }

    fun solution(K: Int, A: IntArray): Int {
        val n = A.size
        var count = 0
        var left = 0

        val minDeque = ArrayDeque<Int>() // Deque to maintain indices of minimum values
        val maxDeque = ArrayDeque<Int>() // Deque to maintain indices of maximum values


        for (right in 0 until n) {
            // Maintain the minDeque: remove elements from the back while the current element is smaller
            while (minDeque.isNotEmpty() && A[minDeque.last()] >= A[right]) {
                minDeque.removeLast()
            }
            minDeque.addLast(right)

            // Maintain the maxDeque: remove elements from the back while the current element is larger
            while (maxDeque.isNotEmpty() && A[maxDeque.last()] <= A[right]) {
                maxDeque.removeLast()
            }
            maxDeque.addLast(right)

            // Adjust the left pointer to maintain the bounded slice condition
            while (A[maxDeque.first()] - A[minDeque.first()] > K) {
                if (minDeque.first() == left) minDeque.removeFirst()
                if (maxDeque.first() == left) maxDeque.removeFirst()
                left++
            }

            // Add the number of slices ending at `right`
            count += right - left + 1
            if (count > 1_000_000_000) return 1_000_000_000 // Return the limit if exceeded
        }


        return count

    }

    fun solution2(K: Int, A: IntArray): Int {
        val n = A.size
        var count = 0

        for (p in 0 until n) {
            var min = A[p]
            var max = A[p]
            for (q in p until n) {
                min = kotlin.math.min(min, A[q])
                max = kotlin.math.max(max, A[q])

                if (max - min <= K) {
                    count++
                    if (count > 1_000_000_000) return 1_000_000_000
                } else {
                    break
                }
            }
        }

        return count
    }

    fun generateString(A: Int, B: Int): String {
        val result = StringBuilder()
        var aCount = A
        var bCount = B

        while (aCount > 0 || bCount > 0) {
            // Check the last two characters in the result
            val lastTwo = if (result.length >= 2) result.substring(result.length - 2) else ""

            if ((lastTwo == "aa" && bCount > 0) || (aCount < bCount)) {
                // Add 'b' if the last two were 'aa' or there are more 'b's left
                result.append('b')
                bCount--
            } else if ((lastTwo == "bb" && aCount > 0) || (bCount <= aCount)) {
                // Add 'a' if the last two were 'bb' or there are more/equal 'a's left
                result.append('a')
                aCount--
            }
        }

        return result.toString()
    }


    fun stringB(A: Int, B: Int): String {

        val sb = StringBuilder()
        val aChar = "a".single()
        val bChar = "b".single()

        var aAdded = 0
        var bAdded = 0

        repeat(A) {
            sb.append(aChar)
        }
        repeat(B) {
            sb.append(bChar)
        }
        var result = sb.toString()
        var resultArr = result.toCharArray()

        if (A == 1) {
            resultArr[0] = bChar
            resultArr[2] = aChar
        } else if (B == 1) {
            resultArr[2] = bChar
            resultArr[resultArr.lastIndex] = aChar
        } else {
            for (char in resultArr.withIndex()) {
                if (char.value.equals(aChar)) {
                    aAdded++
                    if (aAdded > 2) {
                        resultArr[char.index] = bChar
                        aAdded = 0
                        bAdded++
                    }
                } else {
                    bAdded++
                    if (bAdded > 2) {
                        resultArr[char.index] = aChar
                        aAdded++
                        bAdded = 0
                    }
                }
            }

            aAdded = 0
            bAdded = 0
            resultArr.forEach {
                if (it == aChar)
                    aAdded++
                else
                    bAdded++
            }

            if (aAdded > A) {
                resultArr[0] = bChar
            }


        }

        sb.clear()
        resultArr.forEach { c ->
            sb.append(c)
        }
        return sb.toString()
    }

    fun calcBmm(N: Int): Int {

        val number2 = 2.0
        var maxpower = 0

        for (i in 1..N / 2) {
            val m = number2.pow(i)
            if (N % m.toInt() == 0) {
                maxpower = maxOf(maxpower, i)
            }
        }

        return maxpower
    }

    fun calcBill(E: String, L: String): Int {

        val entranceFee = 2
        val firstHourBill = 3
        val afterFirstBill = 4

        val entranceHour = E.split(":").first().toInt()
        val entranceMin = E.split(":").last().toInt()

        val exitHour = L.split(":").first().toInt()
        val exitMin = L.split(":").last().toInt()

        var hoursBill = 0
        var minsBill = 0



        repeat(exitHour - entranceHour - 1) {
            hoursBill += afterFirstBill
        }

        if (hoursBill >= afterFirstBill) {
            hoursBill += firstHourBill
        }

        if (exitMin - entranceMin > 0) {
            if (exitHour - entranceHour > 1)
                minsBill += afterFirstBill
            else if (exitHour - entranceHour == 1 || exitHour - entranceHour == 0)
                minsBill += firstHourBill
        }

        return hoursBill + minsBill + entranceFee
    }


    @Test
    fun `check if users are loaded`() {

    }

    @Test
    fun addition_isCorrect() {
//        assertEquals(2, maxProfit(intArrayOf(2, 1, 2, 1, 0, 1, 2)))
//        assertEquals(5, maxProfit(intArrayOf(7, 1, 5, 3, 6, 4)))
//        assertEquals(0, maxProfit(intArrayOf(7, 6, 4, 3, 1)))
//        assertEquals(4, maxProfit(intArrayOf(3, 2, 6, 5, 0, 3)))

        maine()

    }

    private fun removeDuplicates(nums: IntArray): Int {
        if (nums.size <= 2) return nums.size

        var k = 2 // Index for placing the next element, allowing at most 2 duplicates
        for (i in 2 until nums.size) {
            if (nums[i] != nums[k - 2]) { // Compare with the element at k-2
                nums[k] = nums[i] // Update the k-th position
                k++ // Increment k
            }
        }
        return k
    }

    suspend fun check() {

    }

    private fun majorityElement(nums: IntArray): Int {


        val itemsMap: MutableMap<Int, Int> = mutableMapOf()

        val numbersC = nums.toHashSet()
        if (numbersC.size == 1)
            return numbersC.first()

        for (num in numbersC) {
            itemsMap[num] = nums.count { it == num }
        }


        return itemsMap.filter { it.value == itemsMap.values.max() }.entries.first().key
    }

    private fun rotate(nums: IntArray, k: Int): IntArray {
        val n = nums.size
        val steps = k % n // To handle cases where k > n
        reverse(nums, 0, n - 1)        // Reverse the entire array
        reverse(nums, 0, steps - 1)   // Reverse the first part
        return reverse(nums, steps, n - 1)   // Reverse the second part
    }

    private fun reverse(nums: IntArray, start: Int, end: Int): IntArray {
        var left = start
        var right = end

        while (left < right) {
            val temp = nums[left]
            nums[left] = nums[right]
            nums[right] = temp
            left++
            right--
        }

        return nums
    }


    fun maxProfit(prices: IntArray): Int {

        //        [3,2,6,5,0,3]
        val allProfits = mutableSetOf<Int>()
        allProfits.add(0)
        if (prices.size == 1)
            return 0

        for (min in prices.indices) {
            for (max in 1..prices.lastIndex) {
                if (prices[max] > prices[min] && max > min) {
                    allProfits.add(prices[max] - prices[min])
                }

            }

        }

        val maxProfit = allProfits.max()
        return maxProfit
    }

    fun maxProfit2(prices: IntArray): Int {

        var minPrice = Int.MAX_VALUE
        var maxProfit = 0

        for (price in prices) {
            // Update the minimum price
            if (price < minPrice) {
                minPrice = price
            } else {
                // Calculate and update the maximum profit
                maxProfit = maxOf(maxProfit, price - minPrice)
            }
        }

        return maxProfit
    }


    data class Marriage(val love: String, val stamina: String, val job: String)

    class MarriageBuilder {
        private var love: String = ""
        private var stamina: String = ""
        private var job: String = ""

        fun love(love: String): MarriageBuilder {
            this.love = love
            return this
        }

        fun stamina(stamina: String): MarriageBuilder {
            this.stamina = stamina
            return this
        }

        fun job(job: String): MarriageBuilder {
            this.job = job
            return this
        }

        fun build(): Marriage {
            return Marriage(love, stamina, job)
        }
    }

    class ComputerBuilder {
        private var cpu: Int = 0
        private var ram: Int = 0
        private var graphic = 0


        fun cpu(cpu: Int): ComputerBuilder {
            this.cpu = cpu
            return this
        }

        fun ram(ram: Int): ComputerBuilder {
            this.ram = ram
            return this
        }

        fun graphic(graphic: Int): ComputerBuilder {
            this.graphic = graphic
            return this
        }

        fun build(): Computer {
            return ExampleUnitTest.Computer(ram, cpu, graphic)
        }
    }

    data class Computer(val ram: Int, val cpu: Int, val graphic: Int)


    class Main {

        var marriage = MarriageBuilder()
            .job("")
            .love("")
            .stamina("")
            .build()


        val gammingPc = ComputerBuilder()
            .ram(9)
            .cpu(0)
            .graphic(2)
            .build()


        fun ok() {
            p(gammingPc)
        }

        fun p(pc: Computer) {

        }
    }


    interface Printer {

        fun boos() {
            println("Boooos")
        }

    }


    class JoosiPrinter() : Printer {

        override fun boos() {
            super.boos()
        }
    }

    class JoosiPrinterAdapter(joosiPrinter: JoosiPrinter) : Printer {

        override fun boos() {
            super.boos()
        }
    }

    class Maind {
        val joosiPrinter = JoosiPrinter()

        val adapter = JoosiPrinterAdapter(joosiPrinter)

        fun ok() {
            adapter.boos()
        }
    }

    interface Joorab {

        fun wear() {
            println("bepoosh joorab")
        }

    }

    class JoorabMeshki : Joorab {
        override fun wear() {
            super.wear()
        }

    }

    fun Joorab.decorate(init: () -> Unit): Joorab {
        return object : Joorab {
            override fun wear() {
                init()
                this@decorate.wear()
            }
        }
    }

    fun ded() {
        val joorabMeshki = JoorabMeshki()
        joorabMeshki.decorate { println("dewdef") }

        joorabMeshki.wear()
    }


    interface PaymentStrategy {
        fun pay(kind: String)
    }

    class BankPayment(private val cardNumber: String) : PaymentStrategy {
        override fun pay(kind: String) {
            println(cardNumber)
        }

    }

    class PaypalPayment(private val serialNum: Int) : PaymentStrategy {

        override fun pay(kind: String) {
            println(serialNum)
        }
    }

    class ShoppingCart(private val paymentStrategy: PaymentStrategy) {
        fun checkOut(ll: String) {
            paymentStrategy.pay(ll)
        }
    }


    fun main() {
        val paypalPayment = PaypalPayment(34)
        val bankPayment = BankPayment("")

        val shoppingCart = ShoppingCart(paypalPayment)
        val bankShoppingCart = ShoppingCart(bankPayment)

        shoppingCart.checkOut("")
        bankShoppingCart.checkOut("")
    }

    interface Product {
        fun create(): String
    }

    class ConcreteProductA : Product {
        override fun create(): String {
            return "Product A"
        }
    }

    class ConcreteProductB : Product {
        override fun create(): String {
            return "Product B"
        }
    }

    interface ProductFactory {
        fun createProduct(): Product
    }

    class ConcreteFactoryA : ProductFactory {
        override fun createProduct(): Product {
            return ConcreteProductA()
        }
    }

    class ConcreteFactoryB : ProductFactory {
        override fun createProduct(): Product {
            return ConcreteProductB()
        }
    }

    fun factorym() {
        val factoryA = ConcreteFactoryA()
        val productA = factoryA.createProduct()
        productA.create()

        val factoryB = ConcreteFactoryB()
        val productB = factoryB.createProduct()
        productB.create()
    }


    interface Pen {
        fun create(): String
    }


    class concretePenA : Pen {

        override fun create(): String {
            return "Pen A"
        }
    }


    class concretePenB : Pen {

        override fun create(): String {
            return "Pen B"
        }
    }

    interface PenFactory {
        fun createPen(): Pen
    }

    class concreteFactoryA : PenFactory {
        override fun createPen(): Pen {
            return concretePenA()
        }
    }

    class concreteFactoryB : PenFactory {
        override fun createPen(): Pen {
            return concretePenB()
        }
    }


    interface Animal {
        fun create(): String
    }


    class concreteBoz : Animal {
        override fun create(): String {
            return "boz"
        }
    }

    class ConcreteCow : Animal {
        override fun create(): String {
            return "Cow"
        }
    }

    interface AnimalFactory {
        fun createAnimal(): Animal
    }

    class ConcreteBozFactory : AnimalFactory {
        override fun createAnimal(): Animal {
            return concreteBoz()
        }
    }

    class ConcreteCowFactory : AnimalFactory {
        override fun createAnimal(): Animal {
            return ConcreteCow()
        }
    }

    fun mkkk() {
        val bozFactory = ConcreteBozFactory()
        val boz = bozFactory.createAnimal()
        println(boz.create())

        val cowFactory = ConcreteCowFactory()
        val cow = cowFactory.createAnimal()
        println(cow.create())
    }


    class Counter {
        private var counter = 0

        fun increment() {
            synchronized(this) {
                for (i in 1..100) {
                    counter++
                    println("Incremented counter: $counter")
                }

            }

        }

        fun getCounter(): Int {
            synchronized(this) {
                return counter
            }
        }
    }

    private suspend fun min() {

        val counter = Counter()
        coroutineScope {
            launch { counter.increment() }
        }

        coroutineScope {
            launch { counter.increment() }
        }



        println("Final count: ${counter.getCounter()}")
    }

    fun flown(): Flow<Int> {
        val minFlow = flow<Int> {
            repeat(3) {
                emit(it + 1)
            }
        }
        return minFlow
    }


    class StandardPrice : Discount {

        override fun apply(price: Double): Double {
            return price * 0.9
        }
    }

    class VipPrice : Discount {
        override fun apply(price: Double): Double {
            return price * 0.8
        }
    }


    interface Discount {
        fun apply(price: Double): Double
    }

    fun calcDiscount(discount: Discount, price: Double): Double {
        return discount.apply(price)
    }


    fun maine() {
        val price = 100.0

        val stPrice = StandardPrice()
        val vipPrice = VipPrice()

        println("Standard Price = ${calcDiscount(stPrice, price)}")
        println("Vip Price = ${calcDiscount(vipPrice, price)}")


        val m = Mik.check()
    }


}

class Mik {

    companion object {
        fun check() {

        }
    }
}