import type { FormRules } from 'element-plus'
import { DateTime } from 'luxon';
import validatorID from '@/utils/validateId'

export class ValidationFactory {
  static createRules(type: string): FormRules {
    switch(type) {
      case 'Coordinate':
        return this.coordinateRules()
      case 'Location':
        return this.locationRules()
      case 'DragonHead':
        return this.dragonheadRules()
      case 'DragonCave':
        return this.dragoncaveRules()
      case 'Person':
        return this.personRules()
      case 'Dragon':
        return this.dragonRules()
      default:
        return {}
    }
  }

  private static dragonRules(): FormRules {
    return {
      name: [
        { required: true, message: 'Name is required', trigger: 'blur' },
        { 
          validator: (rule, value, callback) => {
            if (value.length < 1) {
              callback(new Error('Name must be not blank'))
            }else if (value.length > 200) {
              callback(new Error('Name must be less than 200 symbols'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ],
      coordinatesId: [
        { required: true, message: 'Coordinates ID is required', trigger: 'blur' },
        { validator: validatorID, trigger: 'change' }
      ],
      caveId: [
        { 
          validator: (rule, value, callback) => {
            if (value) {
              validatorID(rule, value, callback)
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ],
      killerId: [
        { 
          validator: (rule, value, callback) => {
            if (value) {
              validatorID(rule, value, callback)
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ],
      headId: [
        { 
          validator: (rule, value, callback) => {
            if (value) {
              validatorID(rule, value, callback)
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ],
      age: [
        { 
          validator: (rule, value, callback) => {
            if (value < 1) {
              callback(new Error('Age must be grater than 0'))
            }else if (value >= 2000000000) {
              callback(new Error('Age must be less than 2000000000'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        },
      ],
      character: [
        { required: true, message: 'Character is required', trigger: 'blur' },
      ]
    }
  }

  private static personRules(): FormRules {
    return {
      name: [
        { required: true, message: 'Name is required', trigger: 'blur' },
        { 
          validator: (rule, value, callback) => {
            if (value.length < 1) {
              callback(new Error('Name must be not blank'))
            }else if (value.length > 200) {
              callback(new Error('Name must be less than 200 symbols'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ],
      hairColor: [
        { required: true, message: 'HairColor is required', trigger: 'blur' },
      ],
      locationId: [
        { 
          validator: (rule, value, callback) => {
            if (value) {
              validatorID(rule, value, callback)
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ],
      birthday: [
        { 
          validator: (rule, value: DateTime, callback) => {
            if (value) {
              const now = DateTime.now()
              
              if (value > now) {
                callback(new Error('Date must be in past'))
              } else if (value < now.minus({ years: 100 })) {
                callback(new Error('Date must be within last 100 years'))
              } else {
                callback()
              }
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ],
      height: [
        { required: true, message: 'Height is required', trigger: 'blur' },
        { 
          validator: (rule, value, callback) => {
            if (value < 1) {
              callback(new Error('Height must be grater than 0'))
            }else if (value > 2000000000) {
              callback(new Error('Height must be less than 2000000000 m'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        },
      ],
      weight: [
        { 
          validator: (rule, value, callback) => {
            if (value < 1) {
              callback(new Error('Weight must be grater than 0'))
            }else if (value > 2000000000) {
              callback(new Error('Weight must be less than 2000000000 kg'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        },
      ],
      passportId: [
        { required: true, message: 'Passport ID is required', trigger: 'blur' },
        { 
          validator: (rule, value, callback) => {
            if (value.length > 24) {
              callback(new Error('Passport ID must be less than 23 symbols'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ],
    }
  }

  private static dragoncaveRules(): FormRules {
    return {
      depth: [
        { 
          validator: (rule, value, callback) => {
            if (value < 1) {
              callback(new Error('Size must be grater than 0'))
            }else if (value > 2000000000) {
              callback(new Error('Size must be less than 2000000000 m'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        },
      ],
    }
  }

  private static dragonheadRules(): FormRules {
    return {
      size: [
        { required: true, message: 'Depth is required', trigger: 'blur' },
        { 
          validator: (rule, value, callback) => {
            if (value < 1) {
              callback(new Error('Depth must be grater than 0'))
            }else if (value > 2000000000) {
              callback(new Error('Depth must be less than 2000000000 m'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        },
      ],
    }
  }

  private static locationRules(): FormRules {
    return {
      x: [
        { required: true, message: 'X coordinate is required', trigger: 'blur' },
        { 
          validator: (rule, value, callback) => {
            if (value < -2000000000) {
              callback(new Error('X must be grater than -2000000000'))
            }else if (value > 2000000000) {
              callback(new Error('X must be less than 2000000000'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        },
      ],
      y: [
        { 
          validator: (rule, value, callback) => {
            if (value < -2000000000) {
              callback(new Error('Y must be grater than -2000000000'))
            }else if (value > 2000000000) {
              callback(new Error('Y must be less than 2000000000'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        },
      ],
      z: [
        { required: true, message: 'Z coordinate is required', trigger: 'blur' },
        { 
          validator: (rule, value, callback) => {
            if (value < -2000000000) {
              callback(new Error('Z must be grater than -2000000000'))
            }else if (value > 2000000000) {
              callback(new Error('Z must be less than 2000000000'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        },
      ],
      name: [
        { required: true, message: 'Name is required', trigger: 'blur' },
        { 
          validator: (rule, value, callback) => {
            if (value.length > 240) {
              callback(new Error('Name length must be less than 241'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ],
    }
  }

  private static coordinateRules(): FormRules {
    return {
      x: [
        { required: true, message: 'X coordinate is required', trigger: 'blur' },
        { 
          validator: (rule, value, callback) => {
            if (value < -999) {
              callback(new Error('X must be greater than -999'))
            }else if (value > 2000000000) {
              callback(new Error('X must be less than 2000000000'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ],
      y: [
        { required: true, message: 'Y coordinate is required', trigger: 'blur' },
        { 
          validator: (rule, value, callback) => {
            if (value >845) {
              callback(new Error('Y must be less than 845'))
            }else if (value < -2000000000) {
              callback(new Error('Y must be less than -2000000000'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ]
    }
  }

}