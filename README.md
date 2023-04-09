# ARTIFICIAL INTELLIGENCE - SPRING 23
              
### 👨‍🏫 : Professor Ernest Davis                     
### 👨‍🎓 : Anh Tran            
### ⏰ : 4 / 10 / 23
                  
## Reinforcement Learning Assignment
               
### ⚙️ Folder Structure
<pre align="left">             
📁AI-Game-Of-Chance📁 ---> 🗀diceGame.java🗀            <br>
                        |                                <br>
                        |                                <br>
                        |                                <br>
                        --> 🗀README.md🗀                <br>
</pre>   
         
                
### ✔️ Compiling/Running Instructions                   

                   
####    INSTRUCTIONS                  

                
- To COMPILE:
                  
                
```
javac diceGame.java
```
                    
                  
- Then, to RUN:
                   
                       
```
java diceGame.java [NDice] [NSides] [LTarget] [UTarget] [M] [NGames]
```


                                                   
####    NOTES                 

               
- Ignore (and not include) '[' and ']' in your command.
- Fill in the '[]' parameters with the following conditions. 
- Make sure all values are filled with a space between each.
- Make sure all values are integers, except 'M' 
- 'M' can be a floating point number or integer
- Make sure all values are larger than 0
            

####    EXAMPLE RUNS
                
<div align="center">========== EXAMPLE 1 ==========</div>  
                              
Specifications:
- NDice = 2
- NSides = 2
- LTarget = 4
- UTarget = 4
- M = 100
- NGames = 100,000
           
Command: 

```
java diceGame.java 2 2 4 4 100 100000
```
                 
<div align="center">========== EXAMPLE 2 ==========</div> 
                      
Specifications:
- NDice = 2
- NSides = 2
- LTarget = 4
- UTarget = 5
- M = 100
- NGames = 100,000
            
Command:

```
java diceGame.java 2 2 4 5 100 100000
```
                 
<div align="center">========== EXAMPLE 3 ==========</div> 
                                   
Specifications:
- NDice = 3
- NSides = 2
- LTarget = 6
- UTarget = 7
- M = 100
- NGames = 100,000
            
Command:

```
java diceGame.java 3 2 6 7 100 100000
```
                 
<div align="center">========== EXAMPLE 4 ==========</div> 
                                   
Specifications:
- NDice = 2
- NSides = 3
- LTarget = 6
- UTarget = 7
- M = 100
- NGames = 100,000
            
Command: 

```
java diceGame.java 2 3 6 7 100 100000
```