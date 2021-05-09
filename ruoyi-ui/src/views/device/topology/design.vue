<template>
  <div>sheji{{topologyId}}</div>
</template>

<script>
  export default {
    name: "TopologyDesign",
    data() {
      return {
        topologyId: undefined,
      }
    },
    created() {
      this.topologyId = this.$route.params && this.$route.params.id;
    }
  }
</script>

<style scoped>

</style>
