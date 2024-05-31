{
  "entities": [
    {
      "Name": "Entity: 3",
      "transform": {
        "position": {
          "x": -1.1120263,
          "y": -0.86538464
        },
        "scale": {
          "x": 1.0,
          "y": 1.0
        },
        "rotation": 0.0
      },
      "Components": [
        {
          "assetId": "f170f197-7d55-4a24-8e84-297ca7d5f890",
          "compType": "Tilemap"
        }
      ]
    },
    {
      "Name": "Camera",
      "transform": {
        "position": {
          "x": 1.0973717,
          "y": 0.78488326
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
          "x": 1.0973692,
          "y": 0.7848838
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