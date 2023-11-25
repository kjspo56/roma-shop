<template>
  <div class="home">
    <div class="album py-5 bg-body-tertiary">
      <div class="container">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
          <div class="col" v-for="(item, idx) in state.items" :key="idx">
            {{ items }}
          <CardBox :item="item"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import CardBox from "@/components/CardBox.vue";
import axios from "axios";
import {reactive} from "vue";

export default {
  name: "HomePage",
  components: {CardBox},
  setup() { //setUp이라고 하면 안되는구나
    const state = reactive({
      items:[]
    })

    axios.get("/api/items").then(({data}) => {
      state.items = data;
    });

    return {state}
  }
}
</script>
<style scoped>
</style>