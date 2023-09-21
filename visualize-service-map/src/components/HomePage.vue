<template>
  <div id="app">
    <DependencyGraph />
    <div style="padding-top: 10px; text-align: center" @click="changeData()">
      <button>Change Data</button>
    </div>
  </div>
</template>


<script>
import DependencyGraph from "./DependencyGraph.vue";
// import * as d3 from "d3";
import data1 from '../assets/mock_data/data1.json'

export default {
  components: {
    DependencyGraph,
  },
  data() {
    return {
      data: null,
      dataList: ["data1.json", "data2.json", "data3.json"],
    };
  },

  mounted() {
    this.changeData();
  },

  methods: {
    changeData() {
      // const dataIndex = Math.floor(Math.random() * this.dataList.length);
      // d3.json('./assets/mock_data/'+this.dataList[dataIndex])
      //   .then((data) => {
      //     this.data = data;
      //   })
      //   .catch((error) => {
      //     console.error(
      //       "Cannot proceed with simulation, failed to  retrieve data."
      //     );

      //     console.log(error);
      //   });

        try {
          this.data = data1
          // console.log(this.data);
        } catch (error) {
          console.log(error);
        }
    },
  },
};
</script>

<style>
</style>