{
  "entities": [
    {
      "Name": "Camera",
      "transform": {
        "position": {
          "x": 1.2727785,
          "y": 0.50578403
        },
        "scale": {
          "x": 1.0,
          "y": 1.0
        },
        "rotation": 0.0
      },
      "Components": [
        {
          "FOV": 6.0,
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
          "x": 1.2727767,
          "y": 0.5057839
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
    }
  ]
}