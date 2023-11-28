<template>
  <div class="cart">
cart page
  </div>
</template>

<script>

import axios from "axios";

export default {
  setup(){
    axios.get("/api/cart/items").then(({data})=>{
      console.log(data);
    })
  }
}
</script>

<style scoped>

</style>