{
  "entities": [
    {
      "Name": "Player",
      "transform": {
        "position": {
          "x": 1.3902096,
          "y": 2.4485214
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
      "Name": "Tilemap",
      "transform": {
        "position": {
          "x": 0.0,
          "y": 0.0
        },
        "scale": {
          "x": 1.0,
          "y": 1.0
        },
        "rotation": 0.0
      },
      "Components": [
        {
          "type": "Sandbox.RoomScript",
          "properties": {
            "comp": {
              "walltexture": {
                "assetId": "24ddd739-f9db-48cd-8544-850244435790"
              },
              "floortexture": {
                "assetId": "8356db31-6ace-47a8-8f15-026bed2896e0"
              }
            }
          },
          "compType": "Base"
        },
        {
          "compType": "Tilemap"
        }
      ]
    }
  ]
}