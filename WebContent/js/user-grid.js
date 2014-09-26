 Ext.define('User', {
     extend: 'Ext.data.Model',
     fields: [
         {name: 'username', type: 'string'},
         {name: 'email',  type: 'string'},
         {name: 'bornDate', type: 'date',dateFormat: 'time'},
         {name: 'password',  type: 'string'},
         {name: 'gender',  type: 'string'}
     ]
 });

 var myStore = Ext.create('Ext.data.Store', {
     model: 'User',
     proxy: {
         type: 'ajax',
         url: '/spring-data-jpa/users',
         reader: {
             type: 'json',
             rootProperty: 'content'
         }
     },
     autoLoad: true
 });

 myStore.load({
	    params: {
	        page: 2,
	        size: 10
	    },
	    callback: function(records, operation, success) {
	    },
	    scope: this
	});
 
 
 var genderStore = Ext.create('Ext.data.Store', {
	    fields: ['gender', 'value'],
	    data : [
	        {"gender":'FEMALE', "value":'FEMALE'},
	        {"gender":'MALE', "value":'MALE'}
	    ]
	});

var gender = Ext.create('Ext.form.ComboBox', {
	    store: genderStore,
	    queryMode: 'local',
	    displayField: 'gender',
	    valueField: 'value'
	});
	

Ext.application({
	name : 'MyApp',
	launch : function() {
		Ext.widget({
			renderTo : Ext.getBody(),
			xtype : 'grid',
			title : 'Grid',
			width : 650,
			height : 300,
			plugins : 'rowediting',
			store :myStore,
			columns : {
				defaults : {
					editor : 'numberfield',
					width : 200
				},
				items : [ {
					text : 'Name',
					dataIndex : 'username',
					flex : 1,
					editor : 'textfield'
				}, {
					text : 'Email',
					dataIndex : 'email',
					editor : 'textfield'
				}, {
					text : 'Born',
					dataIndex : 'bornDate',
					renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
					editor : {
						xtype : 'datefield',
						format : 'Y-m-d'
					}
				}, {
					text : 'Gender',
					dataIndex : 'gender',
					editor : gender
				} ]
			}
		});
	}
});