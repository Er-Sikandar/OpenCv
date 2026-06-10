package `in`.droom.target37

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import `in`.droom.target37.alermServiceWorkManager.AlermServiceWorkManagerActivity


class MainActivity : AppCompatActivity() {
    private val TAG="MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        startCode()
        startActivity(Intent(this@MainActivity, AlermServiceWorkManagerActivity::class.java))
        finish()
    }

    private fun startCode() {
        val res=longestUniqueSubstringLength("abcabb")
        println("Res: ${res}")
        val arr=intArrayOf(2, 7, 11, 15)
        val resSum=twoSum(arr,13)
        println("Res Sum: ${resSum.joinToString()}")

        /**
         * Hy-Order Fun
         */

        val multi:(Int,Int)->Int={a,b-> a*b }
        val multi2={a:Int,b:Int->a*b}
    }
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val map=mutableMapOf<Int,Int>()
        for(i in nums.indices){
            val cmp=target-nums[i]
            if(map.containsKey(cmp)){
                return intArrayOf(map[cmp]!!,i)
            }
            map[nums[i]]=i
        }
        return intArrayOf()
    }
    fun longestUniqueSubstringLength(str:String):Int{
        val map = mutableMapOf<Char, Int>()
        var start=0
        var maxLen=0
        for(i in str.indices){
            if (map.containsKey(str[i]) && map[str[i]]!! >= start) {
                start=map[str[i]]!!+1
            }
            map[str[i]]=i
            maxLen=maxOf(maxLen,i-start+1)
        }
        return maxLen
    }


}