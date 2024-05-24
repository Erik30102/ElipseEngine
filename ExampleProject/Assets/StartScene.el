{
  "entities": [
    {
      "Name": "Camera",
      "transform": {
        "position": {
          "x": 0.0,
          "y": 1.4776118
        },
        "scale": {
          "x": 1.0,
          "y": 1.0
        },
        "rotation": 0.0
      },
      "Components": [
        {
          "zoom": 6.0,
          "isActive": true,
          "compType": "Camera"
        },
        {
          "type": "Sandbox.CameraScript",
          "properties": {
            "comp": {}
          },
          "compType": "Base"
        }
      ]
    },
    {
      "Name": "Player",
      "transform": {
        "position": {
          "x": 0.0,
          "y": 1.4776117
        },
        "scale": {
          "x": 0.9925065,
          "y": 1.0
        },
        "rotation": 0.0
      },
      "Components": [
        {
          "texAssetId": "850013df-74d4-464c-b9cc-ec39280cbf69",
          "compType": "Sprite"
        },
        {
          "isRotationLocked": false,
          "compType": "RidgetBody"
        },
        {
          "compType": "BoxCollider"
        },
        {
          "type": "Sandbox.ExampleLib",
          "properties": {
            "comp": {
              "movementSpeed": 100.0,
              "decellSpeed": 5.0
            }
          },
          "compType": "Base"
        }
      ]
    },
    {
      "Name": "Entity: 3",
      "transform": {
        "position": {
          "x": -1.0,
          "y": 2.035763
        },
        "scale": {
          "x": 1.0,
          "y": 1.0
        },
        "rotation": 0.0
      },
      "Components": [
        {
          "texAssetId": "8356db31-6ace-47a8-8f15-026bed2896e0",
          "compType": "Sprite"
        }
      ]
    }
  ]
}