const { response } = require('express');
const express = require('express');
const fetch = (...args) =>
  import('node-fetch').then(({ default: fetch }) => fetch(...args));
const app = express();
const cors = require('cors')

app.use(auth)
app.use(cors())
app.use('/',express.static("dist/date12"));

function auth(req,res,next){
    const api_url='https://api.artsy.net/api/tokens/xapp_token?client_id=2122e95753f69f8e0a5f&client_secret=2fd9c65f5bff753517811cca6e6edea4'
 fetch(api_url,{
    method:"POST"
 })
 .then(response => response.json())
 .then(body =>{
    const token = body["token"];
    req.admin = token;
    next()
 })    
}


app.get("/search/:name",auth,(req,res) =>{
 //   console.log("search token:"+req.admin);
  //  console.log("try")
    url = "https://api.artsy.net/api/search?q="+req.params.name+"&size=10";
    fetch(url,{
        method:'GET',
        headers:{"X-XAPP-Token": req.admin }
    })
    .then(response => response.json())
    .then(
        data=>{
            const search_res = data["_embedded"]['results']
            res.send(search_res)
        }
    )
})
   
app.get("/api_artists/:id",auth,(req,res) =>{
 //   console.log("search token:"+req.admin);
    url = "https://api.artsy.net/api/artists/"+req.params.id;
    fetch(url,{
        method:'GET',
        headers:{"X-XAPP-Token": req.admin }
    })
    .then(response => response.json())
    .then(
        data=>{
         //   console.log(data)
            res.send(data)
        }
    )
})

app.get("/api_artworks/:id",auth,(req,res) =>{
  //  console.log("search token:"+req.admin);
    url = "https://api.artsy.net/api/artworks?artist_id="+req.params.id+"&size=10";
    fetch(url,{
        method:'GET',
        headers:{"X-XAPP-Token": req.admin }
    })
    .then(response => response.json())
    .then(
        data=>{
          //  console.log(data)
            res.send(data)
        }
    )
})

app.get("/api_genes/:id",auth,(req,res) =>{
  //  console.log("search token:"+req.admin);
    url = "https://api.artsy.net/api/genes?artwork_id="+req.params.id;
    fetch(url,{
        method:'GET',
        headers:{"X-XAPP-Token": req.admin }
    })
    .then(response => response.json())
    .then(
        data=>{
           // console.log(data)
            res.send(data)
        }
    )
})


const PORT = parseInt(process.env.PORT) || 8080;
app.listen(PORT, () => {
  console.log(`App listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});