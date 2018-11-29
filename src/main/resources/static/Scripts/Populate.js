//fichier VUE.js


var vm_buildings = new Vue({
  el: "#building",
  data: {
    buildings: [] //crée un tableau buildings vide
  },
  created() {
    let url = 'https://faircorp-app-deguise.cleverapps.io/api/buildings'
    axios.get(url)
    .then(response => {this.buildings = response.data});
  },
  methods: {
    getbat: function() {
      let url = 'https://faircorp-app-deguise.cleverapps.io/api/buildings'
      axios.get(url)
      .then(response => {this.buildings = response.data});
    },

    detbat: function(del_id) {
      let url = 'https://faircorp-app-deguise.cleverapps.io/api/buildings/' + del_id
      axios.delete(url)
      .then(response => this.getbat() );
    }

  }

})


var vm_rooms = new Vue({
   el: "#room",
   data: {
     rooms: []
   },
   created () {
     let url = 'https://faircorp-app-deguise.cleverapps.io/api/rooms'
     axios.get(url)
     .then(response => {this.rooms = response.data});
   },
   methods: {
     getrm: function() {
       let url = 'https://faircorp-app-deguise.cleverapps.io/api/rooms'
       axios.get(url)
       .then(response => {this.rooms = response.data});
       // fetch(get_url)  //plus simple que xmlhttprequest
       // .then(response => response.json())
       // .then(json => {
       //   this.rooms = json //rempli le building avec le json
       // })
     },

     detrm: function(del_id) {
       let url = 'https://faircorp-app-deguise.cleverapps.io/api/rooms/' + del_id
       axios.delete(url)
       .then(response => this.getrm() );
     }
  }


})


var vm_lights = new Vue({
  el: "#light",
  data: {
    lights: [] //crée un tableau buildings vide
  },
  created() {
    let url = 'https://faircorp-app-deguise.cleverapps.io/api/lights'
    axios.get(url)
    .then(response => {this.lights = response.data});
  },
  methods: {
    getlt: function() {
      let url = 'https://faircorp-app-deguise.cleverapps.io/api/lights'
      axios.get(url)
      .then(response => {this.lights = response.data});
      // fetch(url)  //plus simple que xmlhttprequest
      //   .then(response => response.json())
      //   .then(json => {
      //   this.lights = json //rempli le building avec le json
      // })
    },

    imagelt: function() {
      if (this.lights.status == ON) {return 1}
      else {return 0}
    },

    switchlt: function(temp_id) {
      let url = 'https://faircorp-app-deguise.cleverapps.io/api/lights/' + temp_id + '/switch'
      axios.put(url)
      .then(response => this.getlt() );
    },

    detlt: function(del_id) {
      let url = 'https://faircorp-app-deguise.cleverapps.io/api/lights/' + del_id
      axios.delete(url)
      .then(response => this.getlt() );
    }

  }
})


var adding = new Vue({
  el: '#sidebar',
  data: {
    bt_message_id: '',
    bt_message_floor: '',
    rm_message_floor: '',
    rm_message_name: '',
    rm_message_bat: '',
    lt_message_level: '',
    lt_message_status: '',
    lt_message_room: '',

  },
  methods: {
    postbt: function () {
      let url = 'https://faircorp-app-deguise.cleverapps.io/api/buildings'
      axios.post(url, {
        "id": this.bt_message_id,
        "numOfFloor": this.bt_message_floor
      },
      {
        headers: {
            'Content-Type': 'application/json',
        }
      })
      .catch(function (error) {
        console.log(error);
      })
      .then(response => vm_buildings.getbat() );
    },

    postrm: function () {
      let url = 'https://faircorp-app-deguise.cleverapps.io/api/rooms'
      axios.post(url, {
        "floor": this.rm_message_floor,
        "name": this.rm_message_name,
        "buildingId": this.rm_message_bat
      },
      {
        headers: {
            'Content-Type': 'application/json',
        }
      })
      .catch(function (error) {
        console.log(error);
      })
      .then(response => vm_rooms.getrm() );
    },

    postlt: function () {
      let url = 'https://faircorp-app-deguise.cleverapps.io/api/lights'
      axios.post(url, {
        "level": this.lt_message_level,
        "status": this.lt_message_status,
        "roomId": this.lt_message_room
      },
      {
        headers: {
            'Content-Type': 'application/json',
        }
      })
      .catch(function (error) {
        console.log(error);
      })
      .then(response => vm_lights.getlt() );
    }

  }


})
